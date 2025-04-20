package com.morpheus.backend.entity.classifications;

import java.math.BigDecimal;

import org.locationtech.jts.geom.MultiPolygon;

import com.morpheus.backend.entity.ClassEntity;
import com.morpheus.backend.entity.User;

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
@Table(name = "classificacao_manual")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationManual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classificacao_manual")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_controle_classificacao",referencedColumnName = "id_controle_classificacao", nullable = false)
    private ClassificationControl classificationControl;
    
    @ManyToOne
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    private ClassEntity clasEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_analista", referencedColumnName = "id_usuario", nullable = false)
    private User userResponsable;

    @Column(name = "coordenadas_manual", columnDefinition = "geometry(MultiPolygon, 4326)")
    private MultiPolygon coordenadas;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal area;

}
