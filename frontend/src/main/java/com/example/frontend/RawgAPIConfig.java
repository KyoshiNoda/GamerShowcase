package com.example.frontend;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
public class RawgAPIConfig {
    public static void getGames(){
        String apiKey = System.getenv("RAWG_API_KEY");
        if (apiKey == null) {
            System.err.println("API key not found in environment variables");
            return;
        }
        String apiUrl = "https://api.rawg.io/api/games?key=" + apiKey + "&dates=2019-09-01,2019-09-30&platforms=18,1,7";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;
        System.out.println(response.body());
    }

}
