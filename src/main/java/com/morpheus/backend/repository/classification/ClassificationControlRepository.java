package com.morpheus.backend.repository.classification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.morpheus.backend.entity.classifications.ClassificationControl;


public interface ClassificationControlRepository extends JpaRepository<ClassificationControl, Long>{

    @Query(
    value = "SELECT * FROM controle_classificacao WHERE id_talhao = :fieldId LIMIT 1",
    nativeQuery = true
    )
    ClassificationControl findByFieldId(Long fieldId);

}
