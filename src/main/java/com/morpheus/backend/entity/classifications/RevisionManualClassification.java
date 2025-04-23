package com.morpheus.backend.entity.classifications;

import org.locationtech.jts.geom.MultiPolygon;

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
@Table(name = "revisao_classificacao_manual")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevisionManualClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_revisao_classificacao_manual")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_controle_classificacao",referencedColumnName = "id_controle_classificacao", nullable = false)
    private ClassificationControl classificationControl;

    @Column(name = "coordenadas_destaque", columnDefinition = "geometry(MultiPolygon, 4326)")
    private MultiPolygon coordenatiesHighLight;

    @Column(name = "comentario", columnDefinition = "text")
    private String comment;

}
