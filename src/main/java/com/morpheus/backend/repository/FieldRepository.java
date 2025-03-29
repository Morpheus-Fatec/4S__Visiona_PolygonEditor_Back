package com.morpheus.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>{
    Optional<Field> getFieldById(Long fieldId);
    @Query(value = """
        SELECT 
            f.id_talhao AS id, 
            f.nome AS nome, 
            fa.nome AS fazenda, 
            c.nome AS cultura,
            f.coordenadas AS coordinates,
            f.estado AS status
        FROM Talhoes f
        JOIN Fazendas fa ON f.id_fazenda = fa.id_fazenda
        LEFT JOIN Culturas c ON f.id_cultura = c.id_cultura
    """, nativeQuery = true)
    List<Object[]> getAllFeatureSimpleDTO();
    
}
