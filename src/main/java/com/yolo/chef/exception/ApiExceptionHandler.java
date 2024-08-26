package com.yolo.chef.exception;


import com.yolo.chef.response.ErrorResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {



    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {

        ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage(), ex.getDetails());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {

        ErrorResponse errorResponse = new ErrorResponse("403", ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }


}
