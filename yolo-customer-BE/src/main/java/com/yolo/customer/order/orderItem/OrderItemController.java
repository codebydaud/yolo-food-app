package com.yolo.customer.order.orderItem;

import com.yolo.customer.utils.ErrorResponse;
import com.yolo.customer.utils.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.Map;

@CrossOrigin
@RestController
public class OrderItemController {

    private final OrderItemService orderItemService;
    public OrderItemController(OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @PreAuthorize("hasAuthority('ROLE_VIEW_ORDER_HISTORY_DETAIL')")
    @GetMapping("/users/orders/{id}/orderitems")
    public ResponseEntity<?> getOrderItemList(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                              @RequestParam(name = "size", defaultValue = "10") Integer size,
                                              @PathVariable("id") Integer orderID) {
        try {
            Page<Map<String, Object>> orderItemsPage = orderItemService.getOrderItemsWithRecipeByOrderId(orderID, page, size);
            Map<String, Object> response = ResponseObject.createPaginatedResponse(
                    "orderItems",
                    orderItemsPage.getContent(),
                    orderItemsPage.getNumber(),
                    orderItemsPage.getSize(),
                    orderItemsPage.getTotalElements(),
                    orderItemsPage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
        }
    }
}
