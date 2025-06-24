package com.diego.chatgpt.strategy.mejoras;

public class PromptEnhancer {

    public String enhance(String input) {
        input = input.trim();

        // Añade interrogación si falta
        if (!input.endsWith("?") && !input.contains("?")) {
            input = "¿" + capitalize(input) + "?";
        } else {
            input = capitalize(input);
        }

        return input;
    }

    private String capitalize(String str) {
        if (str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
