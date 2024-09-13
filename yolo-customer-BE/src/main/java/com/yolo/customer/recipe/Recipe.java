package com.yolo.customer.recipe;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name="recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "serving_size")
    private Integer servingSize;

    @Column(name = "chef_code")
    private String chefCode;

    @Column(name = "chef_name", nullable = false)
    private String chefName;


    @Column(name = "price", columnDefinition = "BIGINT UNSIGNED")
    private BigInteger price;

    @Column(name = "code", unique = true, length = 8)
    private String code;

    @Column(name = "idea_id", nullable = false)
    private Integer ideaId;

    @Column(name = "currency_id", nullable = false)
    private Integer currencyId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
