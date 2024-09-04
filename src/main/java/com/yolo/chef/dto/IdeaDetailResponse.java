package com.yolo.chef.dto;

import com.yolo.chef.dietaryRestriction.DietaryRestriction;
import com.yolo.chef.interest.Interest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdeaDetailResponse {
    private Idea idea;
    @Getter
    @Setter
    public static class Idea {
        private String customerName;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private Integer recipeCount;
        private List<String> interests;
        private List<String> dietaryRestrictions;

    }
}