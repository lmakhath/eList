package com.elist.knormal.client;

import io.javalin.http.Context;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ERestClient {

    private HttpClient client;
    private HttpRequest request;
    private static ERestClient instance;

    private ERestClient() {
        client = HttpClient.newHttpClient();
    }

    public static ERestClient getInstance () {
        if(instance == null) {
            instance = new ERestClient();
        }
        return instance;
    }

    public String doRequest(String url) {
        request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        String responseBody = null;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                responseBody = response.body();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}
