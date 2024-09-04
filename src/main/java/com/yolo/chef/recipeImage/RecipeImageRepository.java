package com.yolo.chef.recipeImage;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Integer> {
    @Query("SELECT r FROM recipe_image r ORDER BY r.id DESC LIMIT 1")
    Optional<RecipeImage> findLastRecordById();
    List<RecipeImage> findByRecipeId(Integer recipeId);

    @Query("SELECT ri.url FROM recipe_image ri WHERE ri.recipeId = :recipeId")
    List<String> findAllUrlsByRecipeId(Integer recipeId);
}
