package com.morpheus.backend.DTO.GeoJsonView.classification;

import java.util.Objects;

import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;

import lombok.Data;

@Data
public class ClassificationFeature {
    private final String type = "Feature";
    private ClassificationProperties properties;
    private GeometryDTO geometry;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClassificationFeature that = (ClassificationFeature) obj;
        
        // Comparando as coordenadas (geometry.coordinates)
        return Objects.equals(this.getGeometry().getCoordinates(), that.getGeometry().getCoordinates());
    }

    @Override
    public int hashCode() {
        // Gerando o hashCode baseado nas coordenadas (geometry.coordinates)
        return Objects.hash(this.getGeometry().getCoordinates());
    }
}
