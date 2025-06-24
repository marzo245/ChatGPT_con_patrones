package com.diego.chatgpt.strategy.mejoras;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class GrammarCorrector {

    private static final String API_URL = "https://api.languagetool.org/v2/check";

    public String correct(String input) {
        try {
            String response = sendRequest(input);
            return applyCorrections(input, response);
        } catch (Exception e) {
            return input + " (corrección fallida)";
        }
    }

    private String sendRequest(String text) throws IOException {
        String data = "language=es&text=" + URLEncoder.encode(text, "UTF-8");

        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(data.getBytes());
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder response = new StringBuilder();

        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }

    private String applyCorrections(String original, String json) {
        JSONObject response = new JSONObject(json);
        JSONArray matches = response.getJSONArray("matches");

        StringBuilder result = new StringBuilder(original);
        int offset = 0;

        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);
            JSONArray replacements = match.getJSONArray("replacements");

            if (replacements.length() == 0) continue;

            int start = match.getInt("offset") + offset;
            int length = match.getInt("length");
            String replacement = replacements.getJSONObject(0).getString("value");

            result.replace(start, start + length, replacement);
            offset += replacement.length() - length;
        }

        return result.toString();
    }
}

