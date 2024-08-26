package com.yolo.chef.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUserIdAndIdeaId(Integer userId, Integer ideaId);
    Recipe findByUserIdAndId(Integer userId, Integer recipeId);
}
