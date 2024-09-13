package com.yolo.customer.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
public class VendorOrderRequest {
    private OrderDetails order;

    @Getter
    @Setter
    public static class OrderDetails {
        private BigInteger total_price;
        private String order_code;
        private String customer_contact_number;
        private String customer_name;
        private Address address;
        private List<OrderItem> order_items;

        @Getter
        @Setter
        public static class Address {
            private String house;
            private String street;
            private String area;
            private String zip_code;
            private String city;
            private String country;
        }

        @Getter
        @Setter
        public static class OrderItem {
            private int quantity;
            private BigInteger price;
            private String recipe_code;
        }
    }
}
