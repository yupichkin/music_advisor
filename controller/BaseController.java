package advisor.controller;

import advisor.Auth;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

abstract class BaseController {
    protected JsonObject request(final String accessToken,
                                 final String apiPath,
                                 final String uriPath) {
        try {
            HttpClient client = HttpClient.newBuilder().build();

            // create GET request
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + accessToken)
                    .uri(URI.create(apiPath + uriPath))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject result = JsonParser.parseString(response.body()).getAsJsonObject();
            if(result.toString().contains("error")) {
                JsonObject error = result.get("error").getAsJsonObject();
                System.out.println(error.get("message").toString());
                return null;
            }
            return result;

        } catch (IOException | InterruptedException e) {
            System.out.println("api request error");
            return null;
        }

    }
}
