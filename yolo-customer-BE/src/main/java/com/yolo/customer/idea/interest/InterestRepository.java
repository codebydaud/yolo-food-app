package com.yolo.customer.idea.interest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Integer> {
    List<Interest> findByIdeaId(Integer ideaId);
}