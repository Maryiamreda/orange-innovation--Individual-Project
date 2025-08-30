package com.example.demo.securingweb;

import com.example.demo.Services.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



//@Component
public class AtlassianCrowdAuthenticationProvider implements AuthenticationProvider {



    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AtlassianCrowdAuthenticationProvider(UserService userService,
                                                PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Compared to the UserDetails load() method,
        // where you only had access to the username,
        // you now have access to the complete authentication attempt,
        // usually containing a username and password.
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }


        return new UsernamePasswordAuthenticationToken(username, null ,  userDetails.getAuthorities());
        // constructor can be safely used  to create Authentication Token
    }
    @Override
    //The supports method tells Spring Security that
    // this provider handles username and password authentication
    public boolean supports(Class<?> authentication) {

        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}