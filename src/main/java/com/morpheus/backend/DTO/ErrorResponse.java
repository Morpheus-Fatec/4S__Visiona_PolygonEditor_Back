package com.morpheus.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String details;
    private int status;
    private String timestamp;
    private String developerMessage;

    public ErrorResponse(String message, String developerMessage) {
        this.message = message;
        this.developerMessage = developerMessage;
    }
}
