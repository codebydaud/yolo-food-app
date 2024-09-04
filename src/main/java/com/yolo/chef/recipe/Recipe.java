package com.yolo.chef.recipe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private BigInteger price;
    @Column(name = "serving_size")
    private Integer servingSize;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private String code;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "idea_id")
    private Integer ideaId;
    @Column(name = "recipe_status_id")
    private Integer recipeStatusId;
    @Column(name = "currency_id")
    private Integer currencyId;
}
