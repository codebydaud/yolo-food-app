package com.yolo.chef.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/ideas/{ideas_id}/recipes")
    public ResponseEntity<RecipeListResponse> getRecipesByIdeaId(@RequestParam Integer ideaId) {
        RecipeListResponse recipeListResponse = recipeService.getAllRecipesByChef(ideaId);
        return ResponseEntity.ok(recipeListResponse);
    }

    @GetMapping("/recipes/{recipe_id}")
    public ResponseEntity<RecipeDetailsResponseWrapper> getRecipeDetailsByRecipeId(@RequestParam Integer recipeId) {
        RecipeDetailsResponseWrapper recipeDetails = recipeService.getRecipeDetailsByRecipeId(recipeId);
        return ResponseEntity.ok(recipeDetails);
    }
}