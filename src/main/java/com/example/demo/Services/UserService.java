package com.example.demo.Services;

import com.example.demo.Services.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return usersRepository.findAll();
    }



    public User getUserByName(String name) {
        return usersRepository.findByName(name);
    }

    public UserDto addNewUser(UserDto userDto) {
        User existingUser = usersRepository.findByName(userDto.getName());
        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles("USER");

        usersRepository.save(user);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = usersRepository.findByName(name);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + name);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(user.getRoles().split(","))
                .build();
    }
}