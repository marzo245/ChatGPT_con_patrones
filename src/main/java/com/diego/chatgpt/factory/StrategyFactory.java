package com.diego.chatgpt.factory;

import com.diego.chatgpt.strategy.*;

public class StrategyFactory {

  public static MessageStrategy get(String mode) {
    switch (mode) {
      case "simple":
        return new SimpleStrategy();
      case "validado":
        return new ValidatedStrategy();
      default:
        throw new IllegalArgumentException("Modo no soportado: " + mode);
    }
  }
}
