package com.yolo.chef.recipe;

import com.yolo.chef.exception.RecipeNotFoundException;
import com.yolo.chef.exception.RecipeStatusInvalidException;
import com.yolo.chef.idea.IdeaService;
import com.yolo.chef.recipeImage.RecipeImageService;
import com.yolo.chef.recipeStatus.RecipeStatusService;
import com.yolo.chef.util.ApiMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IdeaService ideaService;
    private final RecipeImageService recipeImageService;
    private final RecipeStatusService recipeStatusService;

    public RecipeListResponse getAllRecipesByChef(Integer ideaId, String status, String sortOrder) {
        List<Recipe> recipes;

        if (status != null) {
            Integer statusId = recipeStatusService.findStatusIdByName(status);
            recipes = recipeRepository.findByUserIdAndIdeaIdAndRecipeStatusId(1, ideaId, statusId);
        } else {
            recipes = recipeRepository.findByUserIdAndIdeaId(1, ideaId);
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            recipes.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
        } else {
            recipes.sort((r1, r2) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()));
        }

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
     public ResponseEntity<Map<String, String>> updateRecipeStatus(Integer recipeId, String status)
     {
         if(status==null || status.isEmpty())
         {
             throw new RecipeStatusInvalidException(ApiMessages.RECIPE_STATUS_EMPTY_ERROR.getMessage(), "Recipe status cannot be empty" );
         }
         Optional<Recipe> recipe = recipeRepository.findByUserIdAndId(1, recipeId);
         if(recipe.isPresent())
         {
             Integer statusId = recipeStatusService.findStatusIdByName(status);
             if(statusId==null)
             {
                 throw new RecipeStatusInvalidException(String.format(ApiMessages.RECIPE_STATUS_INVALID_ERROR.getMessage(), status), "Please give correct status");
             }
             recipe.get().setRecipeStatusId(statusId);
             recipeRepository.save(recipe.get());
             Map<String, String> response = new HashMap<>();
             response.put("message", "Recipe status updated successfully");
             return ResponseEntity.status(HttpStatus.OK).body(response);
         }
         else {
             throw new RecipeNotFoundException(ApiMessages.RECIPE_NOT_FOUND.getMessage(),"The Recipe Against Recipe Id : " + recipeId +" Not Found" );
         }
     }

    public ResponseEntity<Map<String, String>> deleteRecipe(Integer recipeId)
    {
        Integer statusId = recipeStatusService.findStatusIdByName("Draft");
        Optional<Recipe> recipe = recipeRepository.findByUserIdAndIdAndRecipeStatusId(1, recipeId, statusId);
        if(recipe.isPresent())
        {
            statusId = recipeStatusService.findStatusIdByName("Archived");
            recipe.get().setRecipeStatusId(statusId);
            recipeRepository.save(recipe.get());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Recipe deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            throw new RecipeNotFoundException(ApiMessages.RECIPE_NOT_FOUND.getMessage(),"The Recipe Against Recipe Id : " + recipeId +" Not Found" );
        }
    }

}
