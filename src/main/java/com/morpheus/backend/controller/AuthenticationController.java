package com.morpheus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.AuthenticationDTO;
import com.morpheus.backend.DTO.LoginResponseDTO;
import com.morpheus.backend.entity.User;
import com.morpheus.backend.repository.UserRepository;
import com.morpheus.backend.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Autowired 
    UserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authDTO){
        User userExists = userRepository.getUserByEmail(authDTO.getEmail());
        String role = "";

        if(userExists != null){
            role = (userExists.getIsAdmin() == true) ? "Administrador" : (userExists.getIsAnalyst() == true) ? "Analista" : (userExists.getIsConsultant() == true) ? "Consultor" : null;
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken( (User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(userExists.getId(), token, userExists.getEmail(), userExists.getName(), role));
    }
}