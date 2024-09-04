package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeNotFoundException extends RuntimeException {
    private String details;

    public RecipeNotFoundException(String message, String details) {
        super(message);
        this.details = details;
    }

}
