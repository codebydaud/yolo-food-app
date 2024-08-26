package com.yolo.chef.recipeImage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeImageService {
    private final RecipeImageRepository recipeImageRepository;

    public List<RecipeImage> findImageByRecipeId(Integer recipeId)
    {
        return recipeImageRepository.findByRecipeId(recipeId);
    }
}
