package com.yolo.chef.recipeStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeStatusRepository extends JpaRepository<RecipeStatus, Integer> {
}
