package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationException extends RuntimeException {
    private String details;

    public UserCreationException(String message, String details) {
        super(message);
        this.details = details;
    }
}
