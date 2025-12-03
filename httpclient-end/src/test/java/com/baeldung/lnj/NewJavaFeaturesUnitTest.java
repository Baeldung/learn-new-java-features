package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

class NewJavaFeaturesUnitTest {

    HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build();

    @Test
    void whenRetrievingData_thenParametersAreIncludedInResponse() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://postman-echo.com/get?param=value"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body()
            .contains("param=value"));
    }

    @Test
    void whenRetrievingData_thenResponseIsHandledAsynchronously() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://postman-echo.com/get"))
            .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<String> futureBody = futureResponse.thenApply(HttpResponse::body);
        CompletableFuture<Void> assertionFuture = futureBody.thenAccept(body -> assertTrue(body.contains("\"host\":\"postman-echo.com\"")));

        assertionFuture.join();
    }

    @Test
    void whenCreatingTask_thenTaskIsCreatedSuccessfully() throws IOException, InterruptedException {
        String jsonBody = "{\"code\":\"task-1\", \"name\":\"Test Task\", \"description\":\"Test POST\"}";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://postman-echo.com/post"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body()
            .contains("Test POST"));
    }
}