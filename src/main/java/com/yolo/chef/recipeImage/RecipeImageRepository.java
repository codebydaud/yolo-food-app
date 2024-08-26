package com.yolo.chef.recipeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Integer> {
    List<RecipeImage> findByRecipeId(Integer recipeId);
}
