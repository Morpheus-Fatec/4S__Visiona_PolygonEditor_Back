package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.UserDto;
import com.morpheus.backend.entity.User;
import com.morpheus.backend.repository.UserRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public String createUser(UserDto userDto){
        try {
            if (userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
                throw new Exception();
                
            }

            int rolesSelected = 0;
            if (Boolean.TRUE.equals(userDto.getIsAdmin())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsConsultant())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsAnalyst())) rolesSelected++;

            if (rolesSelected != 1) {
                throw new Exception("Você deve selecionar exatamente um tipo de usuário (Admin, Consultant ou Analyst).");
            }

            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setIsAdmin(userDto.getIsAdmin());
            user.setIsAnalyst(userDto.getIsConsultant());
            user.setIsConsultant(userDto.getIsAnalyst());
            user.setPassword(userDto.getPassword());
            userRepository.save(user);

            return "Usuário Criado Com Sucesso!";   

        } catch (Exception e) {
            throw new DefaultException("Verifique se foi preenchido todos os campos corretamente.");
            
        }

    }

    public List<User> getAllUsers(){
        List<User> userList = userRepository.findAll();

        try {
            if(userList.size() == 0){
                throw new Exception();
            }

            return userList;

        } catch (Exception e) {
           throw new DefaultException("Não há nenhum usuário cadastro.");
        }
        
    }


    public String updateUser(Long userId, UserDto userDto) {
        try {
            User user = userRepository.getUserById(userId);
    
            if (user == null) {
                throw new Exception("Usuário não encontrado.");
            }
    
            if (userDto.getEmail() == null || userDto.getEmail().isEmpty()
                    || userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
                throw new Exception("Email e senha são obrigatórios.");
            }
    

            int rolesSelected = 0;
            if (Boolean.TRUE.equals(userDto.getIsAdmin())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsConsultant())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsAnalyst())) rolesSelected++;
    
            if (rolesSelected != 1) {
                throw new Exception("Você deve selecionar exatamente um tipo de usuário (Admin, Consultant ou Analyst).");
            }
    
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setIsAdmin(Boolean.TRUE.equals(userDto.getIsAdmin()));
            user.setIsConsultant(Boolean.TRUE.equals(userDto.getIsConsultant()));
            user.setIsAnalyst(Boolean.TRUE.equals(userDto.getIsAnalyst()));
    
            userRepository.save(user);
    
            return "Usuário atualizado com sucesso.";
        } catch (Exception e) {
            return "Erro ao atualizar usuário: " + e.getMessage();
        }
    }

    public String deleteUserById(Long id){
        String deletedUser = "";
        try {
            User user = userRepository.getUserById(id);

            if (user == null) {
                throw new Exception();
            }

            deletedUser = user.getEmail();
            userRepository.deleteById(id);

            return deletedUser + " foi deletado com sucesso!";

        } catch (Exception e) {
            throw new DefaultException("Não existe fazenda com o id " + id);
        }
    }

    
}
