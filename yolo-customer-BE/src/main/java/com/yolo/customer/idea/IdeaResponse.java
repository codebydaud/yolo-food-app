package com.yolo.customer.idea;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class IdeaResponse {
    private Integer ideaId;
    private String title;
    private String description;
    private List<String> interests;
    private List<String> dietaryRestrictions;
    private String ideaStatus;
    private LocalDateTime createdAt;


}
