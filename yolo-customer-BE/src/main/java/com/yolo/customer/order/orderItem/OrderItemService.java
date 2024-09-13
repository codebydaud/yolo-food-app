package com.yolo.customer.order.orderItem;

import com.yolo.customer.recipe.Recipe;
import com.yolo.customer.recipe.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final RecipeRepository recipeRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, RecipeRepository recipeRepository) {
        this.orderItemRepository = orderItemRepository;
        this.recipeRepository = recipeRepository;
    }

    public Page<Map<String, Object>> getOrderItemsWithRecipeByOrderId(Integer orderId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItem> orderItemPage = orderItemRepository.findByOrderIdOrderByCreatedAtDesc(orderId, pageable);

        List<Map<String, Object>> orderItemsWithRecipes = orderItemPage.stream()
                .map(this::mapToOrderItemWithRecipe)
                .collect(Collectors.toList());

        return new PageImpl<>(orderItemsWithRecipes, pageable, orderItemPage.getTotalElements());
    }

    private Map<String, Object> mapToOrderItemWithRecipe(OrderItem orderItem) {
        Recipe recipe = recipeRepository.findById(orderItem.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + orderItem.getRecipeId()));

        Map<String, Object> orderItemWithRecipe = new HashMap<>();
        orderItemWithRecipe.put("orderItem", orderItem);
        orderItemWithRecipe.put("recipe", recipe);

        return orderItemWithRecipe;
    }
}
