package com.example.demo.securingweb;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {
    private final Map<String, String> tokenUserMap = new ConcurrentHashMap<>();

    public void storeToken(String token, String username) {
        tokenUserMap.put(token, username);
        System.out.println("Token stored for user: " + username);
    }

    public String getUser(String token) {
        return tokenUserMap.get(token);
    }

    public void removeToken(String token) {
        tokenUserMap.remove(token);
    }

    public boolean contains(String token) {
        boolean exists = tokenUserMap.containsKey(token);
        System.out.println("Token exists in store: " + exists);
        return exists;
    }

    public void clearExpiredTokens() {
        // You might want to implement token cleanup logic here
        tokenUserMap.clear();
    }
}
