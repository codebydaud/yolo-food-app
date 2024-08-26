package com.yolo.chef.response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String error;
    private String message;
    private String details;
    public ErrorResponse(String error,String message,String details)
    {
        this.error=error;
        this.message=message;
        this.details=details;
    }
}
