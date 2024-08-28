package com.yolo.chef.orderStatus;

import com.yolo.chef.recipeStatus.RecipeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<RecipeStatus, Integer> {
    Optional<OrderStatus> findByValueAndIsActive(String name, Integer isActive);
}
