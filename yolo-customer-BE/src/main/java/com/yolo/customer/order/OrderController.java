package com.yolo.customer.order;

import com.yolo.customer.order.orderStatus.OrderStatus;
import com.yolo.customer.order.dto.OrderRequest;
import org.springframework.web.client.HttpClientErrorException;
import com.yolo.customer.utils.ErrorResponse;
import com.yolo.customer.utils.ResponseObject;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //@PreAuthorize("hasAuthority('')")//use vendor side role here
    @PatchMapping("/users/orders/{order_code}")
    public ResponseEntity<?> updateOrder(
            @PathVariable String order_code,
            @RequestBody Map<String, String> requestBody) {

        String orderStatus = requestBody.get("order_status");
        if (orderStatus == null || orderStatus.isEmpty()) {
            return ResponseEntity.badRequest().body("Order status is required");
        }

        try {
            orderService.updateOrderStatus(order_code, orderStatus);
            return ResponseEntity.ok(ResponseObject.createDataResponse("orders", Map.of("status", orderStatus)));
        } catch (EntityNotFoundException e) {
            log.warn("Entity not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.create(HttpStatus.NOT_FOUND, "Not Found", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.create(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Internal server error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                            ex.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_VIEW_ORDER_HISTORY')")
    @GetMapping("/users/orders")
    public ResponseEntity<?> getOrderList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "status", required = false) String status) {

        try {
            Page<Order> orders = orderService.findAll(page, size, status);
            Map<String, Object> response = ResponseObject.createPaginatedResponse(
                    "orders",
                    orders.getContent(),
                    orders.getNumber(),
                    orders.getSize(),
                    orders.getTotalElements(),
                    orders.getTotalPages()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.create(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage()));
        } catch (Exception ex) {
            log.error("Internal server error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                            ex.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_PLACE_ORDER')")
    @PostMapping("/users/orders")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {

            boolean isOrderPlaced = orderService.placeOrder(orderRequest);
            return ResponseEntity.ok(new ResponseObject<>(true, "Order placed successfully", null));
        } catch (EntityNotFoundException e) {
            log.warn("Entity not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.create(HttpStatus.NOT_FOUND, "Not Found", e.getMessage()));
        } catch (HttpClientErrorException.BadRequest e) {
            log.warn("Illegal argument: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ErrorResponse.create(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage()));
        } catch (Exception ex) {
            log.error("Internal server error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
        }
    }
}
