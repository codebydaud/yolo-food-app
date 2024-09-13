package com.yolo.customer.order.orderItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    Page<OrderItem> findByOrderIdOrderByCreatedAtDesc(Integer orderId, Pageable pageable);

}
