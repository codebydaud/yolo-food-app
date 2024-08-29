//package com.yolo.chef.order;
//
//import com.yolo.chef.exception.OrderInvalidException;
//import com.yolo.chef.exception.OrderStatusInvalidException;
//import com.yolo.chef.exception.RecipeNotFoundException;
//import com.yolo.chef.exception.RecipeStatusInvalidException;
//import com.yolo.chef.orderItem.OrderItem;
//import com.yolo.chef.orderItem.OrderItemRepository;
//import com.yolo.chef.orderStatus.OrderStatusService;
//import com.yolo.chef.recipe.Recipe;
//import com.yolo.chef.recipe.RecipeRepository;
//import com.yolo.chef.recipeStatus.RecipeStatusService;
//import com.yolo.chef.user.UserRepository;
//import com.yolo.chef.util.ApiMessages;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//
//    private final OrderRepository orderRepository;
//    private final OrderItemRepository orderItemRepository;
//    private final UserRepository userRepository;
//    private final RecipeRepository recipeRepository;
//    private final OrderStatusService orderStatusService;
//
//    public ResponseEntity<Map<String, String>> updateOrderStatus(Integer orderId, String status)
//    {
//        if(status==null || status.isEmpty())
//        {
//            throw new OrderStatusInvalidException(ApiMessages.ORDER_STATUS_EMPTY_ERROR.getMessage(), "Order status cannot be empty" );
//        }
//        Optional<Order> order=orderRepository.findById(orderId);
//        if(order.isEmpty())
//        {
//            throw new OrderInvalidException(ApiMessages.ORDER_INVALID_ERROR.getMessage(), "Order does not exist" );
//        }
//        List<OrderItem> orderItems= orderItemRepository.findByOrderId(orderId);
//        List<Recipe> recipes =recipeRepository.findByUserId(1);
//
//        Set<Integer> orderItemRecipeIds = orderItems.stream()
//                .map(OrderItem::getRecipeId)
//                .collect(Collectors.toSet());
//
//        // Check if any Recipe's recipeId matches with an OrderItem's recipeId
//
//        boolean hasMatchingRecipe = recipes.stream()
//                .anyMatch(recipe -> orderItemRecipeIds.contains(recipe.getId()));
//
//        if(hasMatchingRecipe)
//        {
//            Integer statusId = orderStatusService.findStatusIdByName(status);
//            if(statusId==null)
//            {
//                throw new OrderStatusInvalidException(String.format(ApiMessages.ORDER_STATUS_INVALID_ERROR.getMessage(), status), "Please give correct status");
//            }
//
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Recipe status updated successfully");
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        }
//
//        else {
//            throw new RecipeNotFoundException(ApiMessages.RECIPE_NOT_FOUND.getMessage(),"The Recipe Against Recipe Id : " + recipeId +" Not Found" );
//        }
//    }
//}
