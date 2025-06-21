package com.diego.chatgpt.strategy;

public class ValidatedStrategy implements MessageStrategy {

  public String process(String message) {
    if (
      message == null || message.length() < 10
    ) throw new IllegalArgumentException("Mensaje demasiado corto");
    return "[Validado] " + message;
  }
}
