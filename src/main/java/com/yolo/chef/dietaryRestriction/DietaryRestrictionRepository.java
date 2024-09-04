package com.yolo.chef.dietaryRestriction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietaryRestrictionRepository extends JpaRepository<DietaryRestriction, Integer> {
    List<DietaryRestriction> findByIdeaId(Integer id);

//    @Query(value="SELECT d.description FROM yolo_chef_be.dietary_restriction d WHERE d.idea_id = :id", nativeQuery = true)
//    public List<String> findDescByIdeaId(@Param("id") Integer id) ;
}
