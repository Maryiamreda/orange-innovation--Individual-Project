package com.example.demo.Controllers;

import com.example.demo.Services.UserService;
import com.example.demo.Services.dto.UserDto;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public Object getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/add")
    public UserDto addCar(@RequestBody UserDto user) {
        return userService.addNewUser(user);
    }
}
