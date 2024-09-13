package com.yolo.customer.idea.dietaryRestriction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietaryRestrictionRepository extends JpaRepository<DietaryRestriction, Integer> {
    List<DietaryRestriction> findByIdeaId(Integer ideaId);
}

