package com.yolo.chef.orderItem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long quantity;
    private Long price;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "recipe_id")
    private Integer recipeId;
    @Column(name = "order_id")
    private Integer orderId;
}

