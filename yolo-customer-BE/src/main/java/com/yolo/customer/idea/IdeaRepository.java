package com.yolo.customer.idea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Integer> {
    Page<Idea> findByIdeaStatusId(Integer ideaStatusId, Pageable pageable);

    Page<Idea> findByIdeaStatusIdAndTitleContainingIgnoreCase(Integer ideaStatusId, String title, Pageable pageable);

    Page<Idea> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Optional<Idea> findByCode(String code);
}
