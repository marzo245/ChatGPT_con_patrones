package com.diego.chatgpt.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAIClient {

  private final String apiKey;

  public OpenAIClient(String apiKey) {
    this.apiKey = apiKey;
  }

  public String sendMessage(String message)
    throws IOException, InterruptedException {
    String body = String.format(
      "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}]}",
      message.replace("\"", "\\\"")
    );

    HttpRequest request = HttpRequest
      .newBuilder()
      .uri(URI.create("https://api.openai.com/v1/chat/completions"))
      .header("Authorization", "Bearer " + apiKey)
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(body))
      .build();

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(
      request,
      HttpResponse.BodyHandlers.ofString()
    );

    // 🧠 Parsear JSON y devolver solo el contenido del mensaje
    JSONObject json = new JSONObject(response.body());
    JSONArray choices = json.getJSONArray("choices");
    JSONObject messageObj = choices.getJSONObject(0).getJSONObject("message");
    String content = messageObj.getString("content");

    // Puedes devolver el texto directamente o como JSON limpio
    return new JSONObject().put("response", content).toString();
  }
}
