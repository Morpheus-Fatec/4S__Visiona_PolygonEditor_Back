package com.morpheus.backend.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ScanTestDTO {
    private List<CreateFieldDTO> fields;
}
