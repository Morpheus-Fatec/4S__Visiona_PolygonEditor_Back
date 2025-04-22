package com.morpheus.backend.DTO;


import java.math.BigDecimal;
import java.util.List;

import org.locationtech.jts.geom.MultiPolygon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.utilities.Converter;
import com.morpheus.backend.utilities.GeoJsonToJTSConverter;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFieldDTO implements Converter {

    private static GeoJsonToJTSConverter geoJsonToJTSConverter = new GeoJsonToJTSConverter();

    private BigDecimal area;
    private String coordinates;
    @NotNull(message = "Cultura não pode ser nula.")
    private String culture;
    @NotNull(message = "Safra não pode ser nula.")
    private String harvest;
    private String nameField;
    @NotNull(message = "Farm não pode ser nulo.")
    private String nameFarm;
    private Float productivity;
    private String soil;
    private List<ClassificationDTO> classification;

    @Override
    public MultiPolygon convertStringToMultiPolygon() throws JsonProcessingException {

        MultiPolygon multiPolygonCooordinates = geoJsonToJTSConverter.convertJsonNode(this.coordinates);
        return multiPolygonCooordinates;
    }
}
