package com.diego.chatgpt.strategy;

import java.util.Arrays;
import java.util.List;

import com.diego.chatgpt.strategy.mejoras.PromptProcessorService;

public class ValidatedStrategy implements MessageStrategy {

    @Override
    public String process(String message) {
        if (message == null || message.trim().length() < 10) {
            throw new IllegalArgumentException("Mensaje demasiado corto");
        }

        PromptProcessorService processor = new PromptProcessorService();
        String enhanced = processor.process(message);

        return "[Validado] " + enhanced;
    }

}
