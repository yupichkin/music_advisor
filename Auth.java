package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Auth {
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String REDIRECT_URI;
    private final String GRANT_TYPE;
    private final String RESPONSE_TYPE;
    private final String authServer;
    private String authCode = null;
    private String accessToken = null;

    public Auth (String authServer) { //sets default settings from Config
        this.authServer = authServer;
        this.CLIENT_ID     = Config.CLIENT_ID;
        this.CLIENT_SECRET = Config.CLIENT_SECRET;
        this.REDIRECT_URI  = Config.REDIRECT_URI;
        this.GRANT_TYPE    = Config.GRANT_TYPE;
        this.RESPONSE_TYPE = Config.RESPONSE_TYPE;
    }

    public void authorize() {
        System.out.println("use this link to request the access code:");
        System.out.println(authServer
                + "/authorize"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE);
        getRequest();
        requestAccessToken();
    }
    public String getAccessToken() {
        return accessToken;
    }

    public boolean isAuthorized() {
        return accessToken != null; //if it's null then not authorized
    }

    private void getRequest() {
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            String query = exchange.getRequestURI().getQuery();
                            String request;
                            if (query != null && query.contains("code")) {
                                authCode = query.substring(5);
                                System.out.println("code received");
                                System.out.println(authCode);
                                request = "Got the code. Return back to your program.";
                            } else {
                                request = "Authorization code not found. Try again.";
                            }
                            exchange.sendResponseHeaders(200, request.length());
                            exchange.getResponseBody().write(request.getBytes());
                            exchange.getResponseBody().close();
                        }
                    }
            );
            System.out.println("waiting for code...");
            while (authCode == null) {
                Thread.sleep(100);
            }
            server.stop(1);

        }
        catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }
    }

    protected void requestAccessToken() {

        System.out.println("Making http request for access_token...");
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(authServer + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=" + GRANT_TYPE
                                + "&code=" + authCode
                                + "&client_id=" + CLIENT_ID
                                + "&client_secret=" + CLIENT_SECRET
                                + "&redirect_uri=" + REDIRECT_URI))
                .build();
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response:" + response.body());
            JsonObject responseParsed = JsonParser.parseString(response.body()).getAsJsonObject();
            accessToken = responseParsed.get("access_token").getAsString();
            System.out.println( "\nSuccess!");

        } catch (InterruptedException | IOException | NullPointerException e) {
            System.out.println("Error response");
        }
    }
}
