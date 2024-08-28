package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInvalidException extends RuntimeException {
    private String details;
    public UserInvalidException(String message, String details) {
        super(message);
        this.details=details;
    }
}