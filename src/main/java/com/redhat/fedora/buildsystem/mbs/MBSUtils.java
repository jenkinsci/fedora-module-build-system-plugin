package com.redhat.fedora.buildsystem.mbs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.fedora.buildsystem.mbs.model.QueryResult;
import com.redhat.fedora.buildsystem.mbs.model.SubmittedRequest;
import hudson.Util;
import hudson.model.TaskListener;
import hudson.util.Secret;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by shebert on 24/07/17.
 */
public class MBSUtils {

    private static final Logger LOGGER = Logger.getLogger(MBSUtils.class.getName());;
    public static final String MBS_URLPREFIX = "/module-build-service/1/module-builds/";

    public static Client createClient() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(clientConfig);

        // Define a quite defensive timeouts
        client.property(ClientProperties.CONNECT_TIMEOUT, 60000);   // 60s
        client.property(ClientProperties.READ_TIMEOUT,    300000);  // 5m
        return client;
    }

    public static Client createClient(String user, String password) {
        ClientConfig clientConfig = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user,
                password);
        clientConfig.register(feature);
        clientConfig.register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(clientConfig);

        // Define a quite defensive timeouts
        client.property(ClientProperties.CONNECT_TIMEOUT, 60000);   // 60s
        client.property(ClientProperties.READ_TIMEOUT,    300000);  // 5m
        return client;
    }

    private static Response getResponse(WebTarget target) {
        Response response = Response.serverError().entity("error").build();
        try {
            long time = System.currentTimeMillis();
            response = target.request(MediaType.APPLICATION_JSON).get();
            LOGGER.finer("getResponse() response time from MBS: " + Util.getTimeSpanString(System.currentTimeMillis() - time)
                    + " for URI: '" +target.getUri() + "'");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return response;
    }

    public static SubmittedRequest submitModuleRequest(String url, String user, String password,
                                                       String module,
                                                       String rev, String branch, TaskListener listener) throws MBSException {
        Client client = createClient(user, password);
        WebTarget target = client.target(augmentUrl(url)).path(MBS_URLPREFIX);
        String payload = buildPayload(module, branch, rev);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(payload));
        String jsonLine = response.readEntity(String.class);
        if (listener == null) {
            LOGGER.info(jsonLine);
        } else {
            listener.getLogger().println(jsonLine);
        }
        if ( (response.getStatusInfo().getFamily() != Response.Status.Family.INFORMATIONAL) &&
                (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)) {
            throw new MBSException(jsonLine);
        }
        try {
            return new ObjectMapper().readerFor(SubmittedRequest.class).readValue(jsonLine);
        } catch (IOException e) {
            if (listener == null) {
                LOGGER.info(e.getMessage());
            } else {
                listener.getLogger().println(e.getMessage());
            }
            e.printStackTrace();
        }
        return null;
    }

    private static String augmentUrl(String url) {
        return url + "?verbose=true";
    }

    public static QueryResult query(String url, TaskListener listener) throws MBSException {
        Client client = createClient();
        WebTarget target = client.target(augmentUrl(url)).path(MBS_URLPREFIX);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        String jsonLine = response.readEntity(String.class);
        if (listener == null) {
            LOGGER.info(jsonLine);
        } else {
            listener.getLogger().println(jsonLine);
        }
        if ( (response.getStatusInfo().getFamily() != Response.Status.Family.INFORMATIONAL) &&
                (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)) {
            throw new MBSException(jsonLine);
        }
        try {
            return new ObjectMapper().readerFor(QueryResult.class).readValue(jsonLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String buildPayload(String module, String branch, String rev) {
        String scm_url = "git://pkgs.fedoraproject.org/modules/" + module + ".git?#" + rev;
        return "{\"scmurl\": \"" + scm_url + "\", \"branch\": \"" + branch + "\"}";
    }
}
