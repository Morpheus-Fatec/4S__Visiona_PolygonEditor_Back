package com.morpheus.backend.DTO.GeoJsonView;

import lombok.Data;

@Data
public class GeometryDTO {

    private String type = "Polygon";
    private String coordinates;

}
