//package com.example.demo.Controllers;
//
//import com.example.demo.Services.TaskService;
//import com.example.demo.Services.UserService;
//import com.example.demo.Services.dto.UserDto;
//import com.example.demo.entities.User;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//
//@RestController
//public class SessionAuthController {
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//private  final TaskService taskService;
//    public SessionAuthController(UserService userService, AuthenticationManager authenticationManager , TaskService taskService) {
//        this.userService = userService;
//        this.authenticationManager = authenticationManager;
//        this.taskService=taskService;
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
//        //method authenticate which when implemented in a class that implements an Authentication Manager
//        // has all the logic for authenticating a user request.
//        Authentication authentication = authenticationManager.authenticate(
//                //get user data from userdetails then compare it then return the result of the auth
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getName(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        //  Store authentication in SecurityContext and HttpSession
//        SecurityContextHolder.getContext().setAuthentication(authentication); //current Authentication object
//        HttpSession session = request.getSession(true); //creates a session for this client
//        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//
//        User user = (User) authentication.getPrincipal();
//        UserDto dto = new UserDto(user.getId(), user.getName());
//
//        return ResponseEntity.ok(dto);
//    }
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        request.getSession().invalidate();
//        return "Logged out";
//    }
//@GetMapping("/check-auth")
//public User checkAuth(HttpServletRequest request) {
//       return taskService.getAuth() ;
//};
//
//
//
//    public static class LoginRequest {
//        private String name;
//        private String password;
//
//        public String getName() { return name; }
//        public void setName(String name) { this.name = name; }
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//
//
//    }
//
//    @PostMapping("/signup")
//    public UserDto addNewUser(@RequestBody UserDto user , HttpServletRequest request) {
//        UserDto createdUser =  userService.addNewUser(user);
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        createdUser.getName(),
//                        user.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//
//        return createdUser;
//
//    }
//}
