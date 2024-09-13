package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdeaNotFoundException extends RuntimeException {
    private String details;

    public IdeaNotFoundException(String message, String details) {
        super(message);
        this.details = details;
    }

}