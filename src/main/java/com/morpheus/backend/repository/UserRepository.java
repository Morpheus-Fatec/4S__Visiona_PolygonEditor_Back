package com.morpheus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.morpheus.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User getUserById(Long id);
    UserDetails findByEmail(String email);

    
}
