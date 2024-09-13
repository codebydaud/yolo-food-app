package com.yolo.customer.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order>  findByUserIdOrderByCreatedAtDesc(Integer userID, Pageable pageable);

    Page<Order> findByOrderStatusIdAndUserIdOrderByCreatedAtDesc(Integer orderStatusId, Integer userId, Pageable pageable);

    Optional<Order> findByCode(String code);
}
