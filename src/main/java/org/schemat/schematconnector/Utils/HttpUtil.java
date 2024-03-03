package org.schemat.schematconnector.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpUtil {



    public static String getAccessToken() {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0IiwiaWF0IjoxNzA4NTM4MzE2fQ.iafhfM40m-tz_KA56QPRaeyiGuQ8PC8HOBEcOFpzB2s";
    }

    public static String getBaseUrl() {
        return "http://localhost/api/v1";
    }

    public static String getAuthorizationHeader() {
        return "Bearer " + getAccessToken();
    }

    public static HttpEntity sendMultiPartRequest(String endpoint, HttpEntity multipart) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(getBaseUrl() + endpoint);
        uploadFile.addHeader("Authorization", getAuthorizationHeader());
        uploadFile.setEntity(multipart);
        try {
            HttpResponse response = httpClient.execute(uploadFile);
            return response.getEntity();
        } catch (IOException e) {
            return null;
        }
    }
}
