package com.yolo.chef.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUserIdAndIdeaId(Integer userId, Integer ideaId);
    Optional<Recipe> findByUserIdAndId(Integer userId, Integer recipeId);
    List<Recipe> findByUserIdAndIdeaIdAndRecipeStatusId(Integer userId, Integer ideaId, Integer status);

}
