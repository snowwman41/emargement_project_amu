package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.UserDTO;
import com.projectIndustriel.server.entities.User;
import com.projectIndustriel.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getUsers(){
        return userRepository.findAll()
                .stream().map(UserService ::toDTO)
                .toList();
    }

//    private static UserDTO getUserDTO(String userId) {
//        return toDTO(getUser(userId));
//    }
//
//    private static User getUser(String userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//    }

    private static UserDTO toDTO(User user) {
        return new UserDTO(
                user.userId,
                user.firstName,
                user.lastName,
                user.email,
                user.getRole()
        );
    }


    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getRole().equals("Student"))
            StudentService.cleanDelete(userId);
        if (user.getRole().equals("Teacher"))
            TeacherService.cleanDelete(userId);
        userRepository.deleteById(userId);
    }

    public void editUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.userId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        if(!userDTO.firstName().trim().isEmpty()) user.firstName = userDTO.firstName();
        if(!userDTO.lastName().trim().isEmpty()) user.lastName = userDTO.lastName();
        if(!userDTO.email().trim().isEmpty()) user.email = userDTO.email();
        userRepository.save(user);
    }
}
