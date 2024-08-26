package com.yolo.chef.recipe;

import com.yolo.chef.idea.IdeaService;
import com.yolo.chef.recipeImage.RecipeImageService;
import com.yolo.chef.recipeStatus.RecipeStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IdeaService ideaService;
    private final RecipeImageService recipeImageService;
    private final RecipeStatusService recipeStatusService;

    public RecipeListResponse getAllRecipesByChef(Integer ideaId) {
        List<Recipe> recipes = recipeRepository.findByUserIdAndIdeaId(1,ideaId);
        return new RecipeListResponse(recipes, ideaService, recipeImageService);
    }
     public RecipeDetailsResponseWrapper getRecipeDetailsByRecipeId(Integer recipeId)
     {
         Recipe recipe = recipeRepository.findByUserIdAndId(1, recipeId);
         return new RecipeDetailsResponseWrapper(recipe, ideaService, recipeImageService, recipeStatusService);
     }
}
