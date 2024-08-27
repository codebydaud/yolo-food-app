package com.yolo.chef.recipeStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeStatusRepository extends JpaRepository<RecipeStatus, Integer> {
    Optional<RecipeStatus> findByValue(String name);
}
