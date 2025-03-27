package com.morpheus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>{
    Field getFieldById(Long fieldId);
    @Query(value = """
        SELECT 
            t.id_talhao AS id, 
            t.nome AS nome, 
            fa.nome AS farmName, 
            c.nome AS culture,
            t.coordenadas AS coordinates,
            t.estado AS status,
            t.area AS area,
            t.safra AS harvest,
            fa.cidade as farmCity,
            fa.estado as farmState
        FROM Talhoes t
        JOIN Fazendas fa ON t.id_fazenda = fa.id_fazenda
        LEFT JOIN Culturas c ON t.id_cultura = c.id_cultura
        """, nativeQuery = true)
        List<Object[]> getAllFeatureSimpleDTO();


}
