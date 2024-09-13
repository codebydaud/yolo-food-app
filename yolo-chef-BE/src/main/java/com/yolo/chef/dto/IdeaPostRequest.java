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

@AllArgsConstructor
@NoArgsConstructor
public class IdeaPostRequest {
    private Idea idea;
    @Getter
    @Setter
    public static class Idea {
        private String customerName;
        private String title;
        private String description;
        private String ideaCode;
        private List<String> interests;
        private List<String> dietaryRestrictions;

    }
}




