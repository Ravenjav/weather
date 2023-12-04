package com.senla.weather.exception;

public class OutsideApiException extends Exception{
    public OutsideApiException() {
        super("error during request to external API");
    }
}
