package com.morpheus.backend.config;

public class GeoJsonParseException extends RuntimeException {

    public GeoJsonParseException(String message) {
        super(message);
    }

    public GeoJsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
