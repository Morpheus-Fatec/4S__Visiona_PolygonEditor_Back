package com.morpheus.backend.DTO.Download.DownloadManual;

import com.morpheus.backend.DTO.Download.DownloadSaida.GeometryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureManualDto {
    private final String type = "Feature";
    private FieldPropertiesManualDto properties;
    private GeometryDto geometry;

}
