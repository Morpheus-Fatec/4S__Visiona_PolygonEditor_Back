package com.morpheus.backend.DTO.Download.DownloadManual;

import com.morpheus.backend.DTO.Download.DownloadSaida.CrsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManualDTO {
    private final String type = "FeatureCollection";
    private final String name = "MAPA_CLASSIF_MANUAL";
    private CrsDto crs;
    private FeatureManualDto features;

}
