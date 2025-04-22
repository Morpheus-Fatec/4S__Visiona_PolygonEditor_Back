package com.morpheus.backend.DTO.Download;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeometryDto {

    private final String type = "MultiPolygon";
    private List<List<List<List<Double>>>> coordinates;

}
