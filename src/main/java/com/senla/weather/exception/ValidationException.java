package com.senla.weather.exception;

public class ValidationException extends Exception{
    public ValidationException() {
        super("invalid data");
    }
}
