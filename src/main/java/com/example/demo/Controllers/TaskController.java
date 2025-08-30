package com.example.demo.Controllers;

import com.example.demo.Services.TaskService;
import com.example.demo.Services.dto.TaskDto;
import com.example.demo.entities.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/me/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/check-auth")
    public ResponseEntity<Boolean> checkAuth() {
        try {
            taskService.getAuth();
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }


    @GetMapping
    public ResponseEntity<List<Task>> getMyTasks() {

        return ResponseEntity.ok(taskService.getUsersTasks());
    }

    @PostMapping("/add")
    public Object addNewTask( @RequestBody TaskDto task) {

        return taskService.addNewTask(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ?
                ResponseEntity.ok("Task deleted successfully") :
                ResponseEntity.notFound().build();
    }
    @PutMapping("/edit/{id}")
    public Task editTask(@PathVariable Long id, @RequestBody TaskDto task) {

        return taskService.editTask(id, task);
    }

}
