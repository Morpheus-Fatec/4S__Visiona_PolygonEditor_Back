package com.morpheus.backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    private String name;
    private String email;
    private String password;
    
    @Schema(defaultValue = "false")
    private Boolean isAdmin = false;

    @Schema(defaultValue = "false")
    private Boolean isConsultant = false;

    @Schema(defaultValue = "false")
    private Boolean isAnalyst = false;
} 
    

