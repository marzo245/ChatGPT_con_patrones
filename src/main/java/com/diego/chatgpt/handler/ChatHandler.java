package com.diego.chatgpt.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.diego.chatgpt.factory.StrategyFactory;
import com.diego.chatgpt.service.OpenAIClient;
import com.diego.chatgpt.strategy.MessageStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class ChatHandler
  implements RequestHandler<Map<String, Object>, String> {

  @Override
  public String handleRequest(Map<String, Object> input, Context context) {
    try {
      String bodyStr = (String) input.get("body");
      ObjectMapper mapper = new ObjectMapper();
      Map<String, String> body = mapper.readValue(bodyStr, Map.class);
      String mode = body.get("mode");
      String message = body.get("message");

      MessageStrategy strategy = StrategyFactory.get(mode);
      String processed = strategy.process(message);

      String apiKey = System.getenv("OPENAI_API_KEY");
      OpenAIClient client = new OpenAIClient(apiKey);
      return client.sendMessage(processed);
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }
}
