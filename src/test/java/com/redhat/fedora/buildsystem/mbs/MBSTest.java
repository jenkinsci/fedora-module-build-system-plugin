package com.redhat.fedora.buildsystem.mbs;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.redhat.fedora.buildsystem.mbs.model.QueryResult;
import com.redhat.fedora.buildsystem.mbs.model.SubmittedRequest;
import hudson.Util;
import hudson.console.AnnotatedLargeText;
import hudson.util.Secret;
import org.apache.commons.compress.utils.IOUtils;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MBSTest {
    private static final int HTTPOK = 200;
    private static final int SERVICE_PORT = 32000;

    @Rule
    public final JenkinsRule jenkins = new JenkinsRule();
    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(SERVICE_PORT);

    private WireMock wireMock;

    @Before
    public void setup() {
        wireMock = new WireMock(SERVICE_PORT);
    }
    /**
     * Utility method for reading files.
     * @param path path to file.
     * @return contents of file.
     */
    public static String readFile(String path) {
        try {
            URL res = MBSTest.class.getResource(path);
            return Util.loadFile(
                    new File(res.toURI()),
                    Charset.forName("UTF-8")
            );
        } catch (IOException e) {
            throw new Error(e);
        } catch (URISyntaxException e) {
            throw new Error(e);
        }
    }

    private ResponseDefinitionBuilder ok(String path, String... args) {
        String body = String.format(readFile(path), args);
        return aResponse()
                .withStatus(HTTPOK)
                .withHeader("Content-Type", "text/json")
                .withBody(body);
    }

    private ResponseDefinitionBuilder error(String path, int statusCode, String... args) {
        String body = String.format(readFile(path), args);
        return aResponse()
                .withStatus(statusCode)
                .withHeader("Content-Type", "text/json")
                .withBody(body);
    }

    private SubmittedRequest submitRequest() {
        return MBSUtils.submitModuleRequest("http://localhost:" + SERVICE_PORT,
                "scott", Secret.fromString("scott"),
                "mymodule", "myrev", "mybranch", null);

    }

    private QueryResult query() {
        return MBSUtils.query("http://localhost:" + SERVICE_PORT,null);
    }

    @Test(expected = MBSException.class)
    public void testUnknownmodule() {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(error("unknownmodule.txt", 500)));
        submitRequest();
    }

    @Test(expected = MBSException.class)
    public void testMissingattribute() {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(error("missingattribute.txt", 400)));
        submitRequest();
    }

    @Test(expected = MBSException.class)
    public void testHashnotfound() {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(error("hashnotfound.txt", 422)));
        submitRequest();
    }

    @Test(expected = MBSException.class)
    public void testAlreadyExists() {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(error("alreadyexists.txt", 409)));
        submitRequest();
    }

    @Test(expected = MBSException.class)
    public void testCommitnotinbranch() {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(error("commitnotinbranch.txt", 400)));
        submitRequest();
    }

    @Test
    public void testSubmit() throws MBSException {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("submitted.txt")));
        SubmittedRequest request = submitRequest();
        assertNotNull(request);
        System.out.println(request.getName());
        assertTrue(request.getName().equals("testmodule"));
        assertTrue(request.getState() == 1);
        assertTrue(request.getTasks().getRpms().getRpmList().containsKey("ed"));
        assertNotNull(request.getScmurl());
    }

    @Test
    public void testRequestWaiting() throws MBSException {
        stubFor(get(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("waiting.txt")));
        QueryResult result = query();
        assertNotNull(result);
        assertNotNull(result.getItems());
        assertTrue(result.getItems().size() == 1);
        assertTrue(result.getItems().get(0).getState() == 1);
        assertNull(result.getItems().get(0).getTimeCompleted());
    }

    @Test
    public void testRequestReady() throws MBSException {
        stubFor(get(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("ready.txt")));
        QueryResult result = query();
        assertNotNull(result);
        assertNotNull(result.getItems());
        assertTrue(result.getItems().size() == 1);
        assertTrue(result.getItems().get(0).getState() == 5);
        assertNotNull(result.getItems().get(0).getTimeCompleted());
        assertTrue(result.getItems().get(0).getTasks().getRpms().getRpmList().containsKey("ed"));
        assertTrue(result.getItems().get(0).getTasks().getRpms().getRpmList().containsKey("module-build-macros"));
    }

    @Test
    public void testNoBuildsInQueue() throws MBSException {
        stubFor(get(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("nobuilds.txt")));
        QueryResult result = query();
        assertNotNull(result);
        assertNotNull(result.getItems());
        assertTrue(result.getItems().size() == 0);
    }

    protected String loadPipelineScript(String name) {
        try {
            return new String(IOUtils.toByteArray(getClass().getResourceAsStream(name)));
        } catch (Throwable t) {
            throw new RuntimeException("Could not read resource:[" + name + "].");
        }
    }

    @Test
    public void simpleSubmit() throws Exception {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("submitted.txt")));
        WorkflowJob p = jenkins.createProject(WorkflowJob.class, "simpleSubmit");
        p.setDefinition(new CpsFlowDefinition(loadPipelineScript("simpleSubmit.groovy"), false));
        WorkflowRun b = p.scheduleBuild2(0).waitForStart();
        assertNotNull(b);
        jenkins.assertBuildStatusSuccess(jenkins.waitForCompletion(b));
        jenkins.assertLogContains("my submission id is: 1", b);
    }

    @Test
    public void simpleQuery() throws Exception {
        stubFor(get(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("ready.txt")));
        WorkflowJob p = jenkins.createProject(WorkflowJob.class, "simpleQuery");
        p.setDefinition(new CpsFlowDefinition(loadPipelineScript("simpleQuery.groovy"), false));
        WorkflowRun b = p.scheduleBuild2(0).waitForStart();
        assertNotNull(b);
        jenkins.assertBuildStatusSuccess(jenkins.waitForCompletion(b));
        jenkins.assertLogContains("Module id: 1 is ready", b);
    }

    @Test
    public void complete() throws Exception {
        stubFor(post(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("submitted.txt")));
        WorkflowJob p = jenkins.createProject(WorkflowJob.class, "complete");
        p.setDefinition(new CpsFlowDefinition(loadPipelineScript("complete.groovy"), false));
        WorkflowRun b = p.scheduleBuild2(0).waitForStart();
        assertNotNull(b);
        Thread.sleep(2000);
        stubFor(get(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("waiting.txt")));
        Thread.sleep(10000);
        stubFor(get(urlMatching(MBSUtils.MBS_URLPREFIX + ".+"))
                .willReturn(ok("ready.txt")));
        jenkins.assertBuildStatusSuccess(jenkins.waitForCompletion(b));
        jenkins.assertLogContains("my submission id is: 1", b);
        jenkins.assertLogContains("Module id: 1 is ready", b);
        List<String> log = b.getLog(1000);
        for (String s: log) {
            System.out.println(s);
        }
    }

}
