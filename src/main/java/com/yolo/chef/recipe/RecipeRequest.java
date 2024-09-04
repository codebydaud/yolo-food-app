package com.yolo.chef.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class RecipeRequest {
    private String name;
    private String description;
    private BigInteger price;
    private Integer serving_size;
    private MultipartFile[] images;
}
