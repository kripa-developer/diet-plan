package com.dietplan.service;

import com.dietplan.dto.AiMealResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiAiService {

    @Value("${google.gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiMealResponse getCompletion(String systemPrompt, String userPrompt) {
        if (apiKey == null || apiKey.trim().isEmpty() || apiKey.contains("YOUR_ACTUAL_API_KEY")) {
            throw new IllegalStateException("Gemini API Key is missing or invalid in application.properties");
        }

        String fullPrompt = systemPrompt + "\n\nUser Request: " + userPrompt;

        // Standard Request Body for Gemini
        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", fullPrompt)
                ))
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String[] fallbackModels = {
            "gemini-2.5-flash",
            "gemini-2.0-flash",
            "gemini-2.5-pro"
        };

        Exception lastException = null;

        for (String model : fallbackModels) {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey.trim();
            try {
                String response = restTemplate.postForObject(url, entity, String.class);
                JsonNode root = objectMapper.readTree(response);
                
                // Extracting text content from the response
                String rawOutput = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
                
                // AI models often wrap JSON in Markdown blocks. We must clean this.
                String cleanedJson = cleanJsonOutput(rawOutput);
                
                return objectMapper.readValue(cleanedJson, AiMealResponse.class);
            } catch (Exception e) {
                lastException = e;
                System.err.println("Gemini model " + model + " failed due to: " + e.getMessage() + ". Trying next fallback model...");
                try {
                    // Small delay before retrying with the next model
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        throw new RuntimeException("Error communicating with Gemini AI (All fallback models failed): " + (lastException != null ? lastException.getMessage() : "Unknown Error"), lastException);
    }

    private String cleanJsonOutput(String raw) {
        String cleaned = raw.trim();
        // Remove Markdown code block syntax if present
        if (cleaned.contains("```json")) {
            cleaned = cleaned.substring(cleaned.indexOf("```json") + 7);
        } else if (cleaned.contains("```")) {
            cleaned = cleaned.substring(cleaned.indexOf("```") + 3);
        }
        if (cleaned.contains("```")) {
            cleaned = cleaned.substring(0, cleaned.lastIndexOf("```"));
        }
        return cleaned.trim();
    }
}
