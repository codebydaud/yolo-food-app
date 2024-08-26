package com.yolo.chef.dietaryRestriction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietaryRestrictionRepository extends JpaRepository<DietaryRestriction, Integer> {

}
