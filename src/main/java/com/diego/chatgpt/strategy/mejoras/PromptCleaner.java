package com.diego.chatgpt.strategy.mejoras;

import java.util.Arrays;
import java.util.List;

public class PromptCleaner {
    private static final List<String> FILLER_WORDS = Arrays.asList("hola", "chat", "gpt", "por favor", "una pregunta");

    public String clean(String prompt) {
        String result = prompt;
        for (String word : FILLER_WORDS) {
            result = result.replaceAll("(?i)\\b" + word + "\\b", "");
        }
        return result.replaceAll("\\s+", " ").trim();
    }
}
