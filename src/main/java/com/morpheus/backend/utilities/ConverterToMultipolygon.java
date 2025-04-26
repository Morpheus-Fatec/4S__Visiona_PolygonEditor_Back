package com.morpheus.backend.utilities;

import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ConverterToMultipolygon {

    public List<List<List<List<Double>>>> converterToMultiPolygon(MultiPolygon multiPolygon) {
        List<List<List<List<Double>>>> coordinates = new ArrayList<>();

        for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) multiPolygon.getGeometryN(i);
            List<List<List<Double>>> polygonCoordinates = new ArrayList<>();

            for (int j = 0; j < polygon.getNumInteriorRing() + 1; j++) {
                LineString ring = (j == 0) ? polygon.getExteriorRing() : polygon.getInteriorRingN(j - 1);
                List<List<Double>> ringCoordinates = new ArrayList<>();

                Coordinate[] coords = ring.getCoordinates();
                for (Coordinate coord : coords) {
                    ringCoordinates.add(Arrays.asList(coord.x, coord.y));
                }

                polygonCoordinates.add(ringCoordinates);
            }

            coordinates.add(polygonCoordinates);
        }

        return coordinates;
    }
}