package com.group.doconnect.service;

import com.group.doconnect.controller.UserController;
import com.group.doconnect.dto.StatusDTO;
import com.group.doconnect.dto.UserRegisterDTO;
import com.group.doconnect.dto.UserResponseDTO;
import com.group.doconnect.dto.UserUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserController userController;

    @Test
    void getUserByUsername() {
        StatusDTO<UserResponseDTO> targetUser = userService.getUserByUsername("Anna2001");
        System.out.println(targetUser);
    }

    @Test
    void loadUserByUsername() {
        UserDetails details = userService.loadUserByUsername("Anna2001");
        System.out.println(details);
    }

    @Test
    void getUserById() {
        StatusDTO<UserResponseDTO> targetUser = userService.getUserById(1l);
        System.out.println(targetUser);
    }


    @Test
    void createUser() {
        UserRegisterDTO newUser1 = new UserRegisterDTO("Anna001", "Anna_Java", "anna@test.com", "password",false);
        StatusDTO<UserResponseDTO> response = userService.createUser(newUser1);
        System.out.println(response);
    }

    @Test
    void getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        System.out.println("User Amount: " + users.size());
        for (UserResponseDTO userResponseDTO : users) {
            System.out.println(userResponseDTO);
        }
    }


    @Test
    void updateUser() {
        UserUpdateDTO updatedAnna = new UserUpdateDTO("AnnaLooovesCooking@@@", false);
        StatusDTO<UserResponseDTO> updatedUser = userService.updateUser(updatedAnna, 6l);
        System.out.println(updatedUser);
    }

    @Test
    void deleteUserById() {
        System.out.println(userService.deleteUserById(1l));
    }

    @Test
    void deleteAllUsers() {
        userService.deleteAllUsers();
    }
}