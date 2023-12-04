package com.senla.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> inputDataException() {
        return new ResponseEntity<>("invalid input date, the valid format is - (YYYY-MM-DD)\n" +
                "or check, may be one of the dates does not exist", HttpStatus.OK);
    }
}
