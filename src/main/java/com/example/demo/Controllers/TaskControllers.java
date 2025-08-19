package com.example.demo.Controllers;

import com.example.demo.Services.TaskService;
import com.example.demo.Services.dto.TaskDto;
import com.example.demo.entities.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tasks")
public class TaskControllers {
    private final TaskService taskService;

    public TaskControllers(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public List<Task> getTasks(@PathVariable Long userId) {
        return taskService.getUsersTasks(userId);
    }


    @PostMapping("/add")
    public Object addNewTask(@PathVariable("userId") Long id, @RequestBody TaskDto task) {
        return taskService.addNewTask(id, task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ?
                ResponseEntity.ok("Task deleted successfully") :
                ResponseEntity.notFound().build();
    }
}
