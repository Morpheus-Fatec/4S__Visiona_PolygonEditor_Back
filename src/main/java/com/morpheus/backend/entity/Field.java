package com.morpheus.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Talhoes")
@Data
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talhao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_leitura", nullable = true)
    private Scan scanning;

    @ManyToOne
    @JoinColumn(name = "id_fazenda",  nullable = false)
    private Farm farm;

    @Column(name = "safra", nullable = false, length = 7)
    private String harvest;

    @ManyToOne
    @JoinColumn(name = "id_cultura", nullable = true)
    private Culture culture;

    @ManyToOne
    @JoinColumn(name = "id_solo")
    private Soil soil;

    @Column(name = "nome", nullable = false, length = 255)
    private String name;

    @Column(name = "area", nullable = false, precision = 10, scale = 2)
    private BigDecimal area;
    
    @Column(name = "produtividade", nullable = false)
    private String productivity;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Status status;

    @Column(name = "coordenadas", nullable = false, columnDefinition = "TEXT")
    private String coordinates;
}