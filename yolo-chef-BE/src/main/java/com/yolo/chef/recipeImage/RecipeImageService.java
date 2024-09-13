package com.yolo.chef.recipeImage;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeImageService {
    private final RecipeImageRepository recipeImageRepository;

    public List<RecipeImage> findImageByRecipeId(Integer recipeId) {
        return recipeImageRepository.findByRecipeId(recipeId);
    }

    public List<String> getAllUrlAgainstId(Integer recipeId) {
        List<String> storedUrls= recipeImageRepository.findAllUrlsByRecipeId(recipeId);
        List<String > imagesName=new ArrayList<>();
        for (String url : storedUrls) {
            int lastDashIndex = url.lastIndexOf('-');

            // Extract the substring after the last '-'
            String extractedWord = url.substring(lastDashIndex + 1);
            imagesName.add(extractedWord);
            System.out.println("URL: " + url);
        }
        return imagesName;


    }
}
