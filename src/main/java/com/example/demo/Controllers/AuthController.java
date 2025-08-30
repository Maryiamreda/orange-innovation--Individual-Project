package com.example.demo.Controllers;

import com.example.demo.Services.TaskService;
import com.example.demo.Services.UserService;
import com.example.demo.Services.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.securingweb.JwtUtil;
import com.example.demo.securingweb.TokenStore;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final TokenStore tokenStore;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, TokenStore tokenStore) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.tokenStore = tokenStore;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );
        String token = jwtUtil.generateToken(loginRequest.getUsername());

        tokenStore.storeToken(token, loginRequest.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenStore.removeToken(token);
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "No token found in request"));
    }



    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }      // Change from getUserName
        public void setUsername(String username) { this.username = username; }  // Change from setUserName
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @PostMapping("/signup")
    public UserDto addNewUser(@RequestBody UserDto user, HttpServletRequest request) {
        UserDto createdUser = userService.addNewUser(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        createdUser.getName(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);


        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return createdUser;

    }
}
