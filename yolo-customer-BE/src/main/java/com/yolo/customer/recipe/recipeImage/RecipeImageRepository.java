package com.yolo.customer.recipe.recipeImage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Integer> {
    Page<RecipeImage> findAllByRecipeId(Integer recipeId, Pageable pageable);
    Optional<RecipeImage> findById(Long id);
}
