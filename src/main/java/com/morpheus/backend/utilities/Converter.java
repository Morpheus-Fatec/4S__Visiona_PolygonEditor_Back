package com.morpheus.backend.utilities;

import org.locationtech.jts.geom.MultiPolygon;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface Converter {

    public MultiPolygon convertStringToMultiPolygon() throws JsonProcessingException;

}
