package com.example.demo.Services;

import com.example.demo.Services.dto.UserDto;
import com.example.demo.entities.Task;
import com.example.demo.entities.TaskStatus;
import com.example.demo.entities.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public class UserService {
    @Autowired
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Object getUsers() {


        return usersRepository.findAll();
    }

    public UserDto addNewUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        usersRepository.save(user);

        return userDto;
    }
}
