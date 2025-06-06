package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.UserDTO;
import com.projectIndustriel.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/edit-user")
    public List<UserDTO> editUser(@RequestBody UserDTO userDTO) {
        userService.editUser(userDTO);
        return userService.getUsers();
    }


}
