package com.morpheus.backend.DTO.Download.DownloadSaida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeaturesDto {

    private final String type = "Feature";
    private FieldPropertiesDto properties;
    private GeometryDto geometry;


}
