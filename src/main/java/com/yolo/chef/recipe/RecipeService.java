package com.yolo.chef.recipe;

import com.yolo.chef.exception.RecipeNotFoundException;
import com.yolo.chef.idea.IdeaService;
import com.yolo.chef.recipeImage.RecipeImageService;
import com.yolo.chef.recipeStatus.RecipeStatusService;
import com.yolo.chef.util.ApiMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
         Optional<Recipe> recipe = recipeRepository.findByUserIdAndId(1, recipeId);
         if(recipe.isPresent())
         {
             return new RecipeDetailsResponseWrapper(recipe.get(), ideaService, recipeImageService, recipeStatusService);
         }
         else {
             throw new RecipeNotFoundException(ApiMessages.RECIPE_NOT_FOUND.getMessage(),"The Recipe Against Recipe Id : " + recipeId +" Not Found" );
         }

     }
}
