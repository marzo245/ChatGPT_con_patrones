package com.diego.chatgpt.strategy;

public class SimpleStrategy implements MessageStrategy {

  public String process(String message) {
    return message;
  }
}
