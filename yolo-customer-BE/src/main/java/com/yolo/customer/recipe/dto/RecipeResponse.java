package com.yolo.customer.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {
    private Integer id;
    private String recipe_name;
    private String description;
    private Integer serving_size;
    private BigInteger price;
    private String idea_code;
    private String recipe_code;
    private String currency_code;
    private String chef_code;
    private String chef_name;
    private LocalDateTime created_at;
    private List<String> images;
}
