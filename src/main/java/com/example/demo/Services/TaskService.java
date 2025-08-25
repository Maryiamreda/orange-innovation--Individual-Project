package com.example.demo.Services;

import com.example.demo.Services.dto.TaskDto;
import com.example.demo.entities.Task;

import com.example.demo.entities.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    public TaskService(TaskRepository taskRepository, UsersRepository usersRepository) {
        this.taskRepository = taskRepository;
        this.usersRepository = usersRepository;


    }

public User getAuth(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User currentUser= usersRepository.findByName(username);
    if (currentUser == null) {
        throw new org.springframework.security.authentication.BadCredentialsException("Not Authorized");

    }
    return  currentUser;
}

    public List<Task> getUsersTasks() {
        User currentUser = getAuth();
        return taskRepository.findByUserId(currentUser.getId());
    }

    public Object addNewTask( TaskDto taskDto ) {
        User currentUser = getAuth();
        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }
        if(taskDto.getDueDate().isBefore(LocalDate.now())){
             throw new RuntimeException("Due date cannot be in the past");
        }
        Task task = new Task(
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStatus(),
                taskDto.getDueDate(),
                currentUser
        );
        return taskRepository.save(task);

    }
    public boolean deleteTask(Long id) {
        User currentUser = getAuth();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("You cannot delete this task");
        }
          taskRepository.deleteById(id);
          return  true;
    }

    public Task editTask(Long id , TaskDto newTask){
        User currentUser = getAuth();

        if (currentUser == null) {
            throw new org.springframework.security.authentication.BadCredentialsException("Not Authorized");
        }

        Task oldTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!oldTask.getUser().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("You cannot edit this task");
        }
        if (Objects.nonNull(newTask.getTitle()) && !"".equalsIgnoreCase(newTask.getTitle())) {
            oldTask.setTitle(newTask.getTitle());
        }
        if (Objects.nonNull(newTask.getDescription()) && !"".equalsIgnoreCase(newTask.getDescription())) {
            oldTask.setDescription(newTask.getDescription());
        }
        if (Objects.nonNull(newTask.getStatus()) && !"".equalsIgnoreCase(String.valueOf(newTask.getStatus()))) {
            oldTask.setStatus(newTask.getStatus());
        }
        if (Objects.nonNull(newTask.getDueDate()) && newTask.getDueDate() instanceof LocalDate ) {
            oldTask.setDueDate(newTask.getDueDate());
        }

return taskRepository.save(oldTask);
    }

}
