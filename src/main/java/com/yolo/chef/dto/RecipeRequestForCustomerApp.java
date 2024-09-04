package com.yolo.chef.dto;

import com.yolo.chef.idea.IdeaService;
import com.yolo.chef.recipe.Recipe;
import com.yolo.chef.recipeImage.RecipeImage;
import com.yolo.chef.recipeImage.RecipeImageService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeRequestForCustomerApp {

    private String recipe_name;
    private String description;
    private Integer serving_size;
    private BigInteger price;
    private String idea_code;
    private String recipe_code;
    private String chef_code;
    private String chef_name;
    private List<String> images;

    public RecipeRequestForCustomerApp(Recipe recipe, IdeaService ideaService, RecipeImageService recipeImageService) {
        this.recipe_name = recipe.getName();
        this.description = recipe.getDescription();
        this.serving_size = recipe.getServingSize();
        this.price = recipe.getPrice();
        this.idea_code = ideaService.findIdeaCodeById(recipe.getIdeaId());  // Assuming this method returns the idea_code
        this.recipe_code = recipe.getCode();  // Assuming the Recipe entity has a getRecipeCode() method
        this.chef_code = "CHEF3";  // Assuming the Recipe entity has a getChefCode() method
        this.chef_name = "Daud Rizvi";  // Assuming the Recipe entity has a getChefName() method
        this.images = mapImages(recipeImageService.findImageByRecipeId(recipe.getId()));
    }

    private List<String> mapImages(List<RecipeImage> images) {
        return images.stream()
                .map(RecipeImage::getUrl)
                .toList();
    }
}
