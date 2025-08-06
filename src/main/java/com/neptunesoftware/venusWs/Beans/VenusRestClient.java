package com.neptunesoftware.venusWs.Beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptunesoftware.venusWs.Models.TrxnSmsList;
import com.neptunesoftware.venusWs.Utils.ApiException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class VenusRestClient {

    private static String USERNAME = "neptune";
    private static String PASSWORD = "p@ssw0rd";

    private static final ObjectMapper mapper = new ObjectMapper();

    private static HttpClient client() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public static <T> CompletableFuture<T> postAsync(String jsonBody, String uri, Class<T> responseType) {
        String url = WsPublisher.getProviderApi() + "/" + uri;
        String authHeader = authHeader();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authHeader)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            return client().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApplyAsync(response -> {
                        try {
                            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                                // Deserialize the JSON response to the specified type
                                return mapper.readValue(response.body(), responseType);
                            } else {
                                throw new CompletionException(
                                        new RuntimeException("HTTP error: " + response.statusCode() +
                                                ", Body: " + response.body()));
                            }
                        } catch (Exception e) {
                            throw new CompletionException("Failed to parse response", e);
                        }
                    });
        } catch (Exception e) {
            CompletableFuture<T> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(
                    new ApiException("-404",
                            "Failed to create or execute HTTP request"
                    )
            );
            return failedFuture;
        }
    }

    private static String authHeader() {
        String auth = USERNAME + ":" + PASSWORD;
        String encodedAuth = Base64.getEncoder().encodeToString(
                auth.getBytes(StandardCharsets.UTF_8));

        return "Basic " + encodedAuth;
    }
}
