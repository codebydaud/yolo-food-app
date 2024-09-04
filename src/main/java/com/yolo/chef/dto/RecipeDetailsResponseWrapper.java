package com.yolo.chef.dto;

import com.yolo.chef.idea.IdeaService;
import com.yolo.chef.recipe.Recipe;
import com.yolo.chef.recipeImage.RecipeImage;
import com.yolo.chef.recipeImage.RecipeImageService;
import com.yolo.chef.recipeStatus.RecipeStatusService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDetailsResponseWrapper {

    private RecipeDetailsResponse recipe;

    public RecipeDetailsResponseWrapper(Recipe recipe, IdeaService ideaService, RecipeImageService recipeImageService, RecipeStatusService recipeStatusService) {
        this.recipe = new RecipeDetailsResponse(recipe, ideaService, recipeImageService, recipeStatusService);
    }
    @NoArgsConstructor
    @Getter
    public static class RecipeDetailsResponse {

        private String recipe_name;
        private String description;
        private Integer serving_size;
        private BigInteger price;
        private String idea_title;
        private String status;
        private LocalDateTime created_at;
        private List<ImageResponse> images;

        public RecipeDetailsResponse(Recipe recipe, IdeaService ideaService, RecipeImageService recipeImageService, RecipeStatusService recipeStatusService) {
            this.recipe_name = recipe.getName();
            this.description = recipe.getDescription();
            this.serving_size = recipe.getServingSize();
            this.price = recipe.getPrice();
            this.idea_title = ideaService.findIdeaTitleById(recipe.getIdeaId());
            this.status = recipeStatusService.findStatusById(recipe.getRecipeStatusId());
            this.created_at = recipe.getCreatedAt();
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

