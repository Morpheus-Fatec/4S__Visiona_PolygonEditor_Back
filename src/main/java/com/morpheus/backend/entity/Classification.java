package com.morpheus.backend.entity;

import java.math.BigDecimal;

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

    @Column(name = "coordenadas_atuais", columnDefinition = "TEXT")
    private String currentCoordinates;

    @Column(name = "coordenadas_originais", nullable = false, columnDefinition = "TEXT")
    private String originalCoordinates;

    @ManyToOne
    @JoinColumn(name = "id_talhao", nullable = false)
    private Field field;

    @ManyToOne
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false) 
    private ClassEntity classEntity;    

    @Column(name = "area", nullable = false, precision = 10, scale = 2)
    private BigDecimal area;
}