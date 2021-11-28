package com.fernando.oliveira.booking.exception;

import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.domain.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionResponseBuilder responseBuilder;

    @ExceptionHandler(TravelerException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(TravelerException exception){
        ExceptionResponse exceptionResponse = responseBuilder.getBusinessException(exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ExceptionResponse exceptionResponse = responseBuilder.buildFieldErrors(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
