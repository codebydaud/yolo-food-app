
package com.yolo.chef.geminiApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIAssistantService {

   @Value("${api.key}")
    private String apiKey;

   @Value("${api.GeminiUrl}")
    private String geminiUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    public AIAssistantService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper=objectMapper;
    }

    public String generateContent(String requestPayload) {
        String url = geminiUrl + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return extractTextFromResponse(response.getBody());
            } else {
                return "Error: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Exception occurred: " + e.getMessage();
        }
    }

    private String extractTextFromResponse(String responseBody) throws Exception {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode candidatesNode = rootNode.path("candidates");

        if (candidatesNode.isArray() && candidatesNode.size() > 0) {
            JsonNode contentNode = candidatesNode.get(0).path("content").path("parts").get(0).path("text");
            return contentNode.asText();
        }
        return "No content found";
    }
}
