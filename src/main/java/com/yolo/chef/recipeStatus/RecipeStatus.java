package com.yolo.chef.recipeStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "recipe_status")
public class RecipeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;

    @Column(name = "value")
    private String value;

    private Integer isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
