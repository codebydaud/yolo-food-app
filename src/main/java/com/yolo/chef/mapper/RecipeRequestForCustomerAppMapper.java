package com.yolo.chef.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.chef.dto.RecipeRequestForCustomerApp;

public class RecipeRequestForCustomerAppMapper {

    public static void printRecipeAsJson(RecipeRequestForCustomerApp recipeRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert the object to a JSON string
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recipeRequest);
            // Print the JSON string
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}