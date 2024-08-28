package com.yolo.chef.order;


import com.yolo.chef.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PatchMapping("/orders/{order_id}")
    public ResponseEntity<Map<String, String>> updateOrderStatus(@PathVariable("order_id") Integer orderId, @RequestBody Map<String, String> requestBody ) throws Exception {
        String status = requestBody.get("status");
        return orderService.updateOrderStatus(orderId, status);
    }

}
