package com.example.demo.Services.dto;

import com.example.demo.entities.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data //Lombok's @Data annotation generates getter methods based on the exact field name
public class TaskDto {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private UserDto user;
}
