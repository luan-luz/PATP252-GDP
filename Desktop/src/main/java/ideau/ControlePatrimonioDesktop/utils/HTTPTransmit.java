package ideau.ControlePatrimonioDesktop.utils;

import ideau.ControlePatrimonioDesktop.model.RespostaHTTP;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPTransmit {
    public RespostaHTTP get(String url) throws Exception {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new RespostaHTTP(response.statusCode(), response.body());
        }
    }

    public RespostaHTTP post(String url, String body) throws Exception {
        try(HttpClient client   = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new RespostaHTTP(response.statusCode(), response.body());
        }
    }
}
