package com.morpheus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.UserDto;
import com.morpheus.backend.entity.User;
import com.morpheus.backend.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    
    @GetMapping("/listarUsuarios")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/cadastrarUsuario")
    public String createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
        @PathVariable("id") Long userId,
        @RequestBody UserDto userDto
    ) {
        String result = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o usuário: " + e.getMessage());
        }
    }

    @GetMapping("/analistName/{idfield}")
    public ResponseEntity<String> getanalistByFieldName(
        @PathVariable("idfield") Long idfield
    ) {
        String result = userService.analistNameByFieldId(idfield);
        return ResponseEntity.ok(result);
    }

    
}
