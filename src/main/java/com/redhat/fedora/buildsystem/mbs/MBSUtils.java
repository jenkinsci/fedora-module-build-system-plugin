package com.redhat.fedora.buildsystem.mbs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.fedora.buildsystem.mbs.model.QueryResult;
import com.redhat.fedora.buildsystem.mbs.model.SubmittedRequest;
import hudson.model.TaskListener;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

/*
 * The MIT License
 *
 * Copyright (c) Red Hat, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class MBSUtils {

    private static final Logger LOGGER = Logger.getLogger(MBSUtils.class.getName());;
    public static final String MBS_URLPREFIX = "/module-build-service/1/module-builds/";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static SubmittedRequest submitModuleRequest(String url, String user, String password,
                                                       String module,
                                                       String rev, String branch, TaskListener listener) throws MBSException {
        OkHttpClient client = getUnsafeOkHttpClient();
        String payload = buildPayload(module, branch, rev);
        try {
            RequestBody body = RequestBody.create(JSON, payload);
            String credential = Credentials.basic(user, password);
            Request request = new Request.Builder()
                    .header("Authorization", credential)
                    .url(augmentUrl(url))
                    .post(body)
                    .build();
            listener.getLogger().println("");
            listener.getLogger().println("Submitting Module Build Request to: " + augmentUrl(url));
            listener.getLogger().println("For module : " + module);
            listener.getLogger().println("For rev    : " + rev);
            listener.getLogger().println("For branch : " + branch);
            listener.getLogger().println("");
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            if (response.isSuccessful()) {
                SubmittedRequest submittedRequest = new ObjectMapper().readerFor(SubmittedRequest.class).readValue(json);
                if (submittedRequest == null) {
                    throw new MBSException("Unable to create SubmittedRequest object from: " + json);
                }
                listener.getLogger().println("Submitted Request ID: " + submittedRequest.getId());
                return submittedRequest;
            }
            throw new MBSException("Call to " + augmentUrl(url) + " returned " + response.code() + " Response was: " + json);
        } catch (IOException e) {
            listener.getLogger().println(e.getMessage());
            e.printStackTrace();
            throw new MBSException(e);
        }
    }

    private static String augmentUrl(String url) {
        return url + "?verbose=true";
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        return new OkHttpClient();
    }

    public static SubmittedRequest queryModule(String baseUrl, String moduleRequestId, TaskListener listener) throws MBSException {
        String moduleUrl = baseUrl + "/" + moduleRequestId;

        OkHttpClient client = getUnsafeOkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(augmentUrl(moduleUrl))
                    .build();

            listener.getLogger().println("");
            listener.getLogger().println("Querying Module Build Requests at: " + augmentUrl(moduleUrl));
            listener.getLogger().println("");
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            if (response.isSuccessful()) {
                SubmittedRequest result = new ObjectMapper().readerFor(SubmittedRequest.class).readValue(json);
                if (result == null) {
                    throw new MBSException("Unable to create SubmittedRequest object from: " + json);
                }
                listener.getLogger().println("Submitted Request ID: " + result.getId());
                return result;
            }
            throw new MBSException("Call to " + augmentUrl(moduleUrl) + " returned " + response.code() + " Response was: " + json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MBSException(e);
        }
    }

    public static QueryResult query(String url, TaskListener listener) throws MBSException {
        OkHttpClient client = getUnsafeOkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(augmentUrl(url))
                    .build();

            listener.getLogger().println("");
            listener.getLogger().println("Querying Module Build Requests at: " + augmentUrl(url));
            listener.getLogger().println("");
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            if (response.isSuccessful()) {
                QueryResult queryResult = new ObjectMapper().readerFor(QueryResult.class).readValue(json);
                if (queryResult == null) {
                    throw new MBSException("Unable to create QueryResult object from: " + json);
                }
                listener.getLogger().println("Query Result returned: " + queryResult.getItems().size() + " item(s)");
                return queryResult;
            }
            throw new MBSException("Call to " + augmentUrl(url) + " returned " + response.code() + " Response was: " + json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MBSException(e);
        }
    }

    private static String buildPayload(String module, String branch, String rev) {
        String scm_url = "git://pkgs.fedoraproject.org/modules/" + module + ".git?#" + rev;
        return "{\"scmurl\": \"" + scm_url + "\", \"branch\": \"" + branch + "\"}";
    }
}
