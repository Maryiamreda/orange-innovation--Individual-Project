package com.example.demo.Controllers;

import com.example.demo.Services.UserService;
import com.example.demo.Services.dto.UserDto;
import com.example.demo.entities.User;

import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<String> getUsers() {

        List<String> names = new ArrayList<>();
        for (User user : userService.getUsers()) {
            names.add(user.getName());
        }
        return names;
    }
    @GetMapping("/me")
    public UserDto getUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByName(username);

        UserDto dto = new UserDto();
        dto.setName(user.getName());
        return dto;
    }

}
