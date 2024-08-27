package com.yolo.chef.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/ideas/{idea_id}/recipes")
    public ResponseEntity<RecipeListResponse> getRecipesByIdeaId(
            @PathVariable("idea_id") Integer ideaId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "sort_order", required = false, defaultValue = "desc") String sortOrder) {

        RecipeListResponse recipeListResponse = recipeService.getAllRecipesByChef(ideaId, status, sortOrder);
        return ResponseEntity.ok(recipeListResponse);
    }


    @GetMapping("/recipes/{recipe_id}")
    public ResponseEntity<RecipeDetailsResponseWrapper> getRecipeDetailsByRecipeId(@PathVariable("recipe_id") Integer recipeId) {

        RecipeDetailsResponseWrapper recipeDetails = recipeService.getRecipeDetailsByRecipeId(recipeId);
        return ResponseEntity.ok(recipeDetails);
    }

    @PatchMapping("/recipes/{recipe_id}")
    public ResponseEntity<Map<String, String>> updateRecipeStatus(@PathVariable("recipe_id") Integer recipeId, @RequestBody Map<String, String> requestBody ) throws Exception {
        String status = requestBody.get("status");
        return recipeService.updateRecipeStatus(recipeId, status);
    }

    @DeleteMapping("/recipes/{recipe_id}")
    public ResponseEntity<Map<String, String>> deleteRecipe(@PathVariable("recipe_id") Integer recipeId ) throws Exception {
        return recipeService.deleteRecipe(recipeId);
    }


}