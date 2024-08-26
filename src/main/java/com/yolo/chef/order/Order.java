package com.yolo.chef.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long price;
    private String code;
    private String customer_contact_number;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer order_status_id;
    private Integer address_id;
}
