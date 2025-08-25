package com.example.demo.Services.dto;

import lombok.Data;

@Data //Lombok's @Data annotation generates getter methods based on the exact field name
public class UserDto {
    private String name;
    private String password;
    // Default constructor
    public UserDto() {}

    // Constructor with parameters
    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
