package com.morpheus;

import com.morpheus.backend.DTO.ErrorResponse;

import lombok.Getter;

@Getter
public class NotFound extends Exception{
    private ErrorResponse errorResponse;

    public NotFound(String message) {
        this.errorResponse = new ErrorResponse(message);
    }
}
