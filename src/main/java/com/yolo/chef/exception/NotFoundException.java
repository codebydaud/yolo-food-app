package com.yolo.chef.exception;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class NotFoundException extends RuntimeException{


    private String details;

    public NotFoundException(String message, String details) {
        super(message);
        this.details = details;
    }

}