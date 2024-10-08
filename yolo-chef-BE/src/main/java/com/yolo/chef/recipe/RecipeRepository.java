package com.yolo.chef.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.DoubleStream;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findById(Integer id);

    List<Recipe> findByIdeaId(Integer id);
    List<Recipe> findByUserIdAndIdeaId(Integer userId, Integer ideaId);

    List<Recipe> findByUserId(Integer userId);

    Optional<Recipe> findByUserIdAndId(Integer userId, Integer recipeId);

    Optional<Recipe> findByUserIdAndIdAndRecipeStatusId(Integer userId, Integer recipeId, Integer recipeStatusId);

    List<Recipe> findByUserIdAndIdeaIdAndRecipeStatusId(Integer userId, Integer ideaId, Integer status);
}
