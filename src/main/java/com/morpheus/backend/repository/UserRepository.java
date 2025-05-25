package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User getUserById(Long id);
    UserDetails findByEmail(String email);
    User getUserByEmail(String email);    
    @Query(value = """
    SELECT u.nome
    FROM controle_classificacao cc
    JOIN usuarios u ON cc.id_analista = u.id_usuario
    WHERE cc.id_talhao = :fieldId
    """, nativeQuery = true)
    String findAnalystNameByFieldId(@Param("fieldId") Long fieldId);

}
