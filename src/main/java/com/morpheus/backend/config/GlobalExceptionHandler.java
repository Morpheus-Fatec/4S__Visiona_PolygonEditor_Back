package com.morpheus.backend.config;

import com.morpheus.backend.DTO.ErrorResponse;
import com.morpheus.exceptions.DefaultException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ErrorResponse> handleDefaultException(DefaultException ex) {
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

    String userMessage = "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.";
    String developerMessage = "Erro interno no servidor: " + ex.getMessage();
    ErrorResponse errorResponse = new ErrorResponse(userMessage, developerMessage);

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
}

    @ExceptionHandler(GeoJsonParseException.class)
    public ResponseEntity<ErrorResponse> handleGeoJsonParseException(GeoJsonParseException ex) {
    ErrorResponse error = new ErrorResponse();
    error.setMessage("Erro ao processar geometria do GeoJSON.");
    error.setDetails(ex.getMessage());
    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setTimestamp(LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}