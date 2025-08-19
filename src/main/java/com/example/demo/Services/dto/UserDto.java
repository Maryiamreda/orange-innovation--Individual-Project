package com.example.demo.Services.dto;

import lombok.Data;

@Data //Lombok's @Data annotation generates getter methods based on the exact field name
public class UserDto {
    private String name;
    private String password;
}
