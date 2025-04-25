package com.morpheus.backend.repository.classification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.morpheus.backend.DTO.GeoJsonView.revisionClassification.RevisionClassificationCollectionDTO;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.RevisionManualClassification;


public interface RevisionManualClassificationRepository extends JpaRepository<RevisionManualClassification, Long>{


    @Query(value = """
        SELECT 
            ca.id_classificacao_automatica AS id,
            ST_AsGeoJSON(ca.coordenadas_destaque) AS coordinates,
            ca.comentario AS comment
        FROM 
            controle_classificacao cc
        JOIN classificacao_automatica ca ON cc.id_controle_classificacao = ca.id_controle_classificacao
        WHERE 
            cc.id_talhao = :fieldId
        """, nativeQuery = true)
    List<RevisionClassificationCollectionDTO> findRevisionClassificationOutByFieldId(Long fieldId);

    void deleteByClassificationControl(ClassificationControl control);   
}
