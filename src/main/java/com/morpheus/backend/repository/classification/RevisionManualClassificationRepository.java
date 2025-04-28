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
            rca.id_controle_classificacao AS id,
            ST_AsGeoJSON(rca.coordenadas_destaque) AS coordinates,
            rca.comentario AS comment
        FROM 
            controle_classificacao cc
        JOIN revisao_classificacao_manual rca ON cc.id_controle_classificacao = rca.id_controle_classificacao
        WHERE 
            cc.id_talhao = :fieldId
        """, nativeQuery = true)
    List<RevisionClassificationCollectionDTO> findRevisionClassificationOutByFieldId(Long fieldId);

    void deleteByClassificationControl(ClassificationControl control);   
}
