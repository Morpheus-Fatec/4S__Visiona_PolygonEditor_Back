package com.morpheus.backend.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Usuarios")
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.isAdmin) {return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_ANALYST"),new SimpleGrantedAuthority("ROLE_CONSULTANT"));}
        else if(this.isAnalyst){return List.of(new SimpleGrantedAuthority("ROLE_ANALYST"));}
        else {return List.of(new SimpleGrantedAuthority("ROLE_CONSULTANT"));}
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
		return true;
	}

    @Override
    public boolean isAccountNonLocked() {
		return true;
	}

    @Override
    public boolean isCredentialsNonExpired() {
		return true;
	}

    @Override
    public boolean isEnabled() {
		return true;
	}
}