package com.fernando.oliveira.booking.domain.builder;

import com.fernando.oliveira.booking.domain.response.ExceptionResponse;
import com.fernando.oliveira.booking.exception.ObjectError;
import com.fernando.oliveira.booking.exception.TravelerException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExceptionResponseBuilder {

    public ExceptionResponse getBusinessException(TravelerException exception){
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();
    }

    public ExceptionResponse buildFieldErrors(MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception
                .getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ObjectError(error.getDefaultMessage(), error.getField()))
                .collect(Collectors.toList());
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Erro nas validaçãoes dos campos")
                .errorList(errorList)
                .build();
    }
}
