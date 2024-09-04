package com.yolo.chef.exception;


import com.yolo.chef.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecipeNotFoundException(RecipeNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IdeaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIdeaNotFoundException(IdeaNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(UserInvalidException.class)
    public ResponseEntity<ErrorResponse> handleUserInvalidException(UserInvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtException(InvalidJwtException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidException(InvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ErrorResponse> handleUserCreationException(UserCreationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(RecipeStatusInvalidException.class)
    public ResponseEntity<ErrorResponse> handleRecipeStatusInvalidException(RecipeStatusInvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDetails());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
