package com.morpheus.backend.DTO.Download;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaidaDTO {

    private final String type = "FeatureCollection";
    private final String name = "BM2_GEOJSON_SAIDA";
    private CrsDto crs;
    private FeaturesDto features;
   
}
