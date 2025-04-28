package com.morpheus.backend.repository.classification;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.entity.classifications.ManualClassification;

public interface ManualClassificationRepository extends JpaRepository<ManualClassification, Long>{

    @Query(value = """
        SELECT 
            cm.id_classificacao_manual AS id,
            cm.area AS area,
            ST_AsGeoJSON(cm.coordenadas_manual) AS coordinates,
            c.nome as classEntity
        FROM 
            controle_classificacao cc
        JOIN classificacao_manual cm ON cc.id_controle_classificacao = cm.id_controle_classificacao
        JOIN classes c ON cm.id_classe = c.id_classe
        WHERE 
            cc.id_talhao = :fieldId
        """, nativeQuery = true)
    List<ClassificationDTO> getManualClassificationByFieldId(Long fieldId);


    List<ManualClassification> findByClassificationControl(ClassificationControl control);

    void deleteByClassificationControl(ClassificationControl control);

}
