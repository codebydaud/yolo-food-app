package com.yolo.chef.geminiApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class AIAssistantController {

    private final AIAssistantService aiAssistantService;
    private final PromptBuilder promptBuilder;
    public AIAssistantController(AIAssistantService aiAssistantService,PromptBuilder promptBuilder) {
        this.aiAssistantService = aiAssistantService;
        this.promptBuilder=promptBuilder;
    }
    @PreAuthorize("hasAnyAuthority('ROLE_CREATE_RECIPE_VIA_AI')")
    @PostMapping("/generateContent")
    public Map<String, Object> generateContent(@RequestBody AIRequest aiRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        String text=promptBuilder.buildPrompt(aiRequest);

        String requestPayload = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", text);

            String aiResponse = aiAssistantService.generateContent(requestPayload);
            // Parse AI response and map to desired format
            try {
                // Here, we assume aiResponse is a JSON string containing RecipeName, RecipeDescription, and ServingSize
                ObjectMapper responseMapper = new ObjectMapper();
                Map<String, Object> aiData = responseMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});

                // Populate the responseMap with values from aiData
                responseMap.put("recipeName", aiData.get("RecipeName"));
                responseMap.put("recipedescription", aiData.get("RecipeDescription"));
                responseMap.put("recipeservingSize", aiData.get("ServingSize"));

            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to parse AI response", e);
            }
            return responseMap;
        }
    }