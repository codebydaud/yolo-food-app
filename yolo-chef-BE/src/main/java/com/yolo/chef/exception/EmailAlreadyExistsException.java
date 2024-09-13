package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAlreadyExistsException extends RuntimeException{
    private String details;
    public EmailAlreadyExistsException(String message, String details) {
        super(message);
        this.details = details;
    }
}