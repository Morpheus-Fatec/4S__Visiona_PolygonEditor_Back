package com.morpheus.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User getUserById(Long id);
    Optional<User> findByEmail(String email);

    
}
