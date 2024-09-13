package com.yolo.chef.dto;

import com.yolo.chef.idea.IdeaService;
import com.yolo.chef.recipe.Recipe;
import com.yolo.chef.recipeImage.RecipeImage;
import com.yolo.chef.recipeImage.RecipeImageService;
import com.yolo.chef.recipeStatus.RecipeStatusService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
public class RecipeListResponse {

    private List<RecipeResponse> recipes;

    public RecipeListResponse(List<Recipe> recipes, IdeaService ideaService, RecipeImageService recipeImageService, RecipeStatusService recipeStatusService) {
        this.recipes = recipes.stream()
                .map(recipe -> new RecipeResponse(recipe, ideaService, recipeImageService, recipeStatusService))
                .toList();
    }
    public List<RecipeResponse> getRecipes() {
        return Collections.unmodifiableList(recipes);
    }


    @NoArgsConstructor
    @Getter
    public static class RecipeResponse {
        private Integer id;
        private String recipe_name;
        private String description;
        private Integer serving_size;
        private BigInteger price;
        private String idea_title;
        private String status;
        private List<ImageResponse> images;

        public RecipeResponse(Recipe recipe, IdeaService ideaService, RecipeImageService recipeImageService, RecipeStatusService recipeStatusService) {
            this.id=recipe.getId();
            this.recipe_name = recipe.getName();
            this.description = recipe.getDescription();
            this.serving_size = recipe.getServingSize();
            this.price = recipe.getPrice();
            this.idea_title = ideaService.findIdeaTitleById(recipe.getIdeaId());
            this.status=recipeStatusService.findStatusById(recipe.getRecipeStatusId());
            this.images = mapImages(recipeImageService.findImageByRecipeId(recipe.getId()));
        }

        private List<ImageResponse> mapImages(List<RecipeImage> images) {
            return images.stream()
                    .map(image -> new ImageResponse(image.getUrl()))
                    .toList();
        }

        public List<ImageResponse> getImages() {
            return Collections.unmodifiableList(images);
        }

        @NoArgsConstructor
        @Getter
        public static class ImageResponse {
            private String url;

            public ImageResponse(String url) {
                this.url = url;
            }
        }
    }
}
