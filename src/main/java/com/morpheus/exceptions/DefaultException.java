package com.morpheus.exceptions;

import com.morpheus.backend.DTO.ErrorResponse;

import lombok.Getter;

@Getter
public class DefaultException extends RuntimeException{
    private final ErrorResponse errorResponse;

    public DefaultException(String message){
        super(message);
        this.errorResponse = new ErrorResponse(message);
    }

}
