package com.yolo.customer.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderRequest {

    private OrderDto order;

    @Getter
    @Setter
    public static class OrderDto {
        private BigInteger totalPrice;
        private String currencyCode;
        private List<OrderItemDto> orderItems = new ArrayList<>();

        public void setOrderItems(List<OrderItemDto> orderItems) {
            this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
        }
    }

    @Getter
    @Setter
    public static class OrderItemDto {
        private int quantity;
        private BigInteger price;
        private int recipeId;
        private String chefName;
    }
}