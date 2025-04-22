package com.morpheus.backend.DTO.Download;

import lombok.Data;

@Data
public class CrsDto {
    private final String type = "name";
    private PropertiesCrsDto properties = new PropertiesCrsDto();
}
