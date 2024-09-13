package com.yolo.chef.interest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Integer> {
    List<Interest> findByIdeaId(Integer id);

//    @Query(value = "SELECT i.description FROM yolo_chef_be.interest i WHERE i.idea_id = :id", nativeQuery = true)
//   public List<String> findDescByIdeaId(@Param("id") Integer id);

}
