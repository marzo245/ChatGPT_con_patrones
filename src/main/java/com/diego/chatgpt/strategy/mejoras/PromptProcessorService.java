package com.diego.chatgpt.strategy.mejoras;

public class PromptProcessorService {

    private final PromptCleaner cleaner = new PromptCleaner();
    private final PromptEnhancer enhancer = new PromptEnhancer();
    private final GrammarCorrector corrector = new GrammarCorrector();
    // private final PromptRewriterClient rewriter = new PromptRewriterClient(); // opcional GPT

    public String process(String prompt) {
        if (prompt == null || prompt.trim().length() < 10) {
            throw new IllegalArgumentException("El prompt es demasiado corto.");
        }

        String cleaned = cleaner.clean(prompt);
        String enhanced = enhancer.enhance(cleaned);

        try {
            String corrected = corrector.correct(enhanced);
            return "[Validado] " + corrected;
        } catch (Exception e) {
            return "[Validado] " + enhanced + " (corrección fallida)";
        }
    }
}
