package com.example.demo.securingweb;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenStore tokenStore;

    @Autowired
    private ApplicationContext applicationContext;

    private UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, TokenStore tokenStore) {
        this.jwtUtil = jwtUtil;
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
//Extract Authorization Header
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
                System.out.println("JWT extracted for user: " + username);
            } catch (Exception e) {
                System.out.println("JWT Token extraction failed: " + e.getMessage());
            }
        } else {
            System.out.println("No Authorization header or header does not start with Bearer");
        }

        // Only try to authenticate if we got a username and the context is empty
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Check token exists in store
            if (tokenStore.contains(token)) {
                try {
                    if (userDetailsService == null) {
                        userDetailsService = applicationContext.getBean(UserDetailsService.class);
                    }

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtil.validateToken(token, username)) {
                        System.out.println("Token valid and in store for user: " + username);

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        System.out.println("Token validation failed for user: " + username);
                    }
                } catch (Exception e) {
                    System.out.println("Authentication failed for user " + username + ": " + e.getMessage());
                }
            } else {
                System.out.println("Token not found in store or already removed.");
            }
        }

        chain.doFilter(request, response);
    }
}
