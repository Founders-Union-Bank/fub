package org.fub.exception;

import org.fub.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ErrorResponse> defaultExceptionHandler(RuntimeException e) {
        ErrorResponse response = ErrorResponse.builder()
                .message(e.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .timeStamp(new Date())
                .build();
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(value = JWTTokenCreationException.class)
    ResponseEntity<ErrorResponse> jwtTokenExceptionHandler(JWTTokenCreationException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Error while creating JWT Token")
                .timeStamp(new Date())
                .build();

        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timeStamp(new Date())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    ResponseEntity<ErrorResponse> noHandlerFoundException(NoHandlerFoundException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .timeStamp(new Date())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
