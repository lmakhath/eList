package com.elist.knormal.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public final class ERestClient {

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

    public String doGetRequest(String url) {
        request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return doRequest(request);
    }

    public String doPostRequest(String url, String name, Object requestObject) {
        var values = new HashMap<String, Object>() {{
            put("name", name);
            put ("request", requestObject);
        }};

        try {
            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(values);
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            return doRequest(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final String doRequest(HttpRequest request) {

        String responseBody = null;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                responseBody = response.body();
            } else
                System.out.println("Status code: " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}
