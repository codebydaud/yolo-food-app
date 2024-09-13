package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistsException extends RuntimeException {
    private String details;

    public UserAlreadyExistsException(String message, String details) {
        super(message);
        this.details = details;
    }
}
