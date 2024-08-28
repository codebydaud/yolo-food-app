package com.yolo.chef.orderStatus;

import com.yolo.chef.recipeStatus.RecipeStatus;
import com.yolo.chef.recipeStatus.RecipeStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public Integer findStatusIdByName(String status) {
        return orderStatusRepository.findByValueAndIsActive(status,1)
                .map(OrderStatus::getId)
                .orElse(null);
    }
}
