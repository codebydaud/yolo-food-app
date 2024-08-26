package com.yolo.chef.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Integer servingSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String code;
    private Integer userId;
    private Integer ideaId;
    private Integer recipeStatusId;
    private Integer currencyId;
}
