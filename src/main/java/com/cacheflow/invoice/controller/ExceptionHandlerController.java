package com.cacheflow.invoice.controller;

import com.cacheflow.invoice.Response.BaseResponse;
import com.cacheflow.invoice.exception.BadRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> badRequestException(BadRequestException ex) {
        BaseResponse message = new BaseResponse(
                null,
                List.of(ex.getMessage())
                );
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> resourceNotFoundException(MethodArgumentTypeMismatchException ex) {
        BaseResponse message = new BaseResponse(
                List.of(),
                List.of("Failed to convert received parameter: " + ex.getValue() + " to property: " + ex.getName())
        );
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseResponse> resourceNotFoundException(ResponseStatusException ex) {
        BaseResponse message = new BaseResponse(
                null,
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}

