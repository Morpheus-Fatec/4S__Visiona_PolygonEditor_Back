package com.morpheus.backend.utilities;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GeoJsonToJTSConverter {

    public MultiPolygon convertJsonNode(String geoJson) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JtsModule());
            JsonNode jsonNode = objectMapper.readTree(geoJson);

            if (!jsonNode.isArray() || jsonNode.size() == 0) {
                throw new IllegalArgumentException("GeoJSON inválido: estrutura de coordenadas não encontrada.");
            }

            return convertToMultiPolygon(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar GeoJSON: " + e.getMessage(), e);
        }
    }
    public static MultiPolygon convertToMultiPolygon(JsonNode jsonNode) {
        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            List<Polygon> polygonList = new ArrayList<>();

            for (int i =0; i < jsonNode.size(); i++){
                JsonNode polygonNode = jsonNode.get(i);
                if(polygonNode.size()< 1 ) continue;

                JsonNode outerRingNode = polygonNode.get(0);
                Coordinate[] outerCoordinates = parseCoordinates(outerRingNode);
                outerCoordinates = ensureClosedRing(outerCoordinates);
                LinearRing outerRing = geometryFactory.createLinearRing(outerCoordinates);

                List<LinearRing> innerRings = new ArrayList<>();
                for (int j = 1; j < polygonNode.size(); j++) {
                    JsonNode innerRingNode = polygonNode.get(j);
                    Coordinate[] innerCoordinates = parseCoordinates(innerRingNode);
                    innerCoordinates = ensureClosedRing(innerCoordinates);
                    innerRings.add(geometryFactory.createLinearRing(innerCoordinates));
                }

                Polygon polygon = geometryFactory.createPolygon(outerRing, innerRings.toArray(new LinearRing[0]));
                polygonList.add(polygon);
            }
            return geometryFactory.createMultiPolygon(polygonList.toArray(new Polygon[0]));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter para MultiPolygon: " + e.getMessage(), e);
        }
    }

    public static Coordinate[] parseCoordinates(JsonNode coordinatesNode) {
        Coordinate[] coordinates = new Coordinate[coordinatesNode.size()];
        for (int i = 0; i < coordinatesNode.size(); i++) {
            JsonNode coordinateNode = coordinatesNode.get(i);
            double x = coordinateNode.get(0).asDouble();
            double y = coordinateNode.get(1).asDouble();
            coordinates[i] = new Coordinate(x, y);
        }
        return coordinates;
    }

    private static Coordinate[] ensureClosedRing(Coordinate[] coordinates) {
        if (coordinates.length > 0 && !coordinates[0].equals2D(coordinates[coordinates.length - 1])) {
            Coordinate[] closedCoordinates = new Coordinate[coordinates.length + 1];
            System.arraycopy(coordinates, 0, closedCoordinates, 0, coordinates.length);
            closedCoordinates[closedCoordinates.length - 1] = coordinates[0];
            return closedCoordinates;
        }
        return coordinates;
    }

}
