package com.morpheus.backend.entity.classifications;

import java.math.BigDecimal;

import org.locationtech.jts.geom.MultiPolygon;

import com.morpheus.backend.entity.ClassEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "classificacao_automatica")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomaticClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classificacao_automatica")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_controle_classificacao",referencedColumnName = "id_controle_classificacao", nullable = false)
    private ClassificationControl classificationControl;

    @Column(name = "coordenadas_automatica", columnDefinition = "geometry(MultiPolygon, 4326)")
    private MultiPolygon coordenadas;

    @ManyToOne
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    private ClassEntity ClassEntity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal area;
}
