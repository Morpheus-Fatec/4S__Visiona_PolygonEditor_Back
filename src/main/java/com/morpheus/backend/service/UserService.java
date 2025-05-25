package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            if (userDto.getEmail().trim().isEmpty()) {
                throw new DefaultException("O e-mail é obrigatório.");
            }
            
            if (userDto.getPassword().trim().isEmpty()) {
                throw new DefaultException("A senha é obrigatória.");
            }
            
            if (userDto.getName().trim().isEmpty() || userDto.getName() == null) {
                throw new DefaultException("O nome é obrigatório.");
            }

            if (userRepository.findByEmail(userDto.getEmail()) != null) {
                throw new DefaultException("Já existe um usuário com esse e-mail cadastrado");
            }

            int rolesSelected = 0;
            if (Boolean.TRUE.equals(userDto.getIsAdmin())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsConsultant())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsAnalyst())) rolesSelected++;

            if (rolesSelected != 1) {
                throw new DefaultException("Você deve selecionar exatamente um tipo de usuário (Admin, Consultant ou Analyst).");
            }
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setIsAdmin(userDto.getIsAdmin());
            user.setIsAnalyst(userDto.getIsAnalyst());
            user.setIsConsultant(userDto.getIsConsultant());
            String encryptPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
            user.setPassword(encryptPassword);
            userRepository.save(user);

            return "Usuário Criado Com Sucesso!";   

        }  catch (DefaultException e) {
            throw e;
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
            UserDetails existingUserOptional = userRepository.findByEmail(userDto.getEmail());
    
            if (user == null) {
                throw new DefaultException("Usuário não encontrado.");
            }
    
            if (userDto.getEmail().trim().isEmpty()) {
                throw new DefaultException("O e-mail é obrigatório.");
            }
            
            if (userDto.getPassword().trim().isEmpty()) {
                throw new DefaultException("A senha é obrigatória.");
            }
            
            if (userDto.getName().trim().isEmpty()) {
                throw new DefaultException("O nome é obrigatório.");
            }

            if (existingUserOptional != null) {
                User existingUser = (User) existingUserOptional;
            
                if (!existingUser.getId().equals(userId)) {
                    throw new DefaultException("Já existe um usuário com esse e-mail cadastrado.");
                }
            }
    
            int rolesSelected = 0;
            if (Boolean.TRUE.equals(userDto.getIsAdmin())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsConsultant())) rolesSelected++;
            if (Boolean.TRUE.equals(userDto.getIsAnalyst())) rolesSelected++;
    
            if (rolesSelected != 1) {
                throw new DefaultException("Você deve selecionar exatamente um tipo de usuário (Admin, Consultant ou Analyst).");
            }
            
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setIsAdmin(Boolean.TRUE.equals(userDto.getIsAdmin()));
            user.setIsConsultant(Boolean.TRUE.equals(userDto.getIsConsultant()));
            user.setIsAnalyst(Boolean.TRUE.equals(userDto.getIsAnalyst()));
    
            userRepository.save(user);
    
            return "Usuário atualizado com sucesso.";
        } catch (DefaultException e) {
            throw e;
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

    
    public String analistNameByFieldId(Long fieldId){
        String analistName = userRepository.findAnalystNameByFieldId(fieldId);
        return analistName;
    }
    
}
