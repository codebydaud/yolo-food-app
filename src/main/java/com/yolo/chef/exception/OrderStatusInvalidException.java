package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusInvalidException extends RuntimeException {
    private String details;
    public OrderStatusInvalidException(String message, String details) {
        super(message);
        this.details=details;
    }
}