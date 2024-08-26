package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
    private String details;

    public BadRequestException(String message, String details) {
        super(message);
        this.details = details;
    }

}
