package com.morpheus.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String name;

    @Column(name = "senha", nullable = false, length = 255)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "administrador", nullable = false)
    private Boolean isAdmin;

    @Column(name = "consultor", nullable = false)
    private Boolean isConsultant;

    @Column(name = "analista", nullable = false)
    private Boolean isAnalyst;
}