package com.morpheus.backend.entity;

import java.math.BigDecimal;

import org.locationtech.jts.geom.MultiPolygon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Classificacoes")
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classificacao")
    private Long id;

    @Column(name = "coordenadas_atuais", columnDefinition = "geometry(MultiPolygon, 4326)")
    private MultiPolygon currentCoordinates;

    @Column(name = "coordenadas_originais", nullable = false, columnDefinition = "geometry(MultiPolygon, 4326)")
    private MultiPolygon originalCoordinates;

    @ManyToOne
    @JoinColumn(name = "id_talhao", nullable = false)
    private Field field;

    @ManyToOne
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    private ClassEntity classEntity;

    @Column(name = "area", nullable = false, precision = 10, scale = 2)
    private BigDecimal area;
}