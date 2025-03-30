package com.morpheus.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>{
    Optional<Field> getFieldById(Long fieldId);
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
            fa.estado as farmState,
            so.nome as soil
        FROM Talhoes t
        JOIN Fazendas fa ON t.id_fazenda = fa.id_fazenda
        LEFT JOIN Culturas c ON t.id_cultura = c.id_cultura
        LEFT JOIN Solos so ON t.id_solo = so.id_solo
        WHERE 
            (COALESCE(:nome, '') = '' OR LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND
            (COALESCE(:soil, '') = '' OR LOWER(so.nome) LIKE LOWER(CONCAT('%', :soil, '%'))) AND
            (COALESCE(:status, '') = '' OR LOWER(t.estado) LIKE LOWER(CONCAT('%', :status, '%'))) AND
            (COALESCE(:culture, '') = '' OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :culture, '%'))) AND
            (COALESCE(:harvest, '') = '' OR LOWER(t.safra) LIKE LOWER(CONCAT('%', :harvest, '%'))) AND
            (COALESCE(:farmName, '') = '' OR LOWER(fa.nome) LIKE LOWER(CONCAT('%', :farmName, '%')))
        """, nativeQuery = true)
    List<Object[]> getAllFeatureSimpleDTO(
        @Param("nome") String name,
        @Param("soil") String soil,
        @Param("status") String status,
        @Param("culture") String culture,
        @Param("harvest") String harvest,
        @Param("farmName") String farmName
    );
    
}
