package com.example.demo.Services;

import com.example.demo.Services.dto.TaskDto;
import com.example.demo.entities.Task;
import com.example.demo.entities.TaskStatus;
import com.example.demo.entities.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    public TaskService(TaskRepository taskRepository, UsersRepository usersRepository) {
        this.taskRepository = taskRepository;
        this.usersRepository = usersRepository;
    }



    public List<Task> getUsersTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }





    public Object addNewTask(Long id , TaskDto taskDto ) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(taskDto.getDueDate().isBefore(LocalDate.now())){
             throw new RuntimeException("Due date cannot be in the past");
        }
        Task task = new Task(
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStatus(),
                taskDto.getDueDate(),
                user
        );


        Object savedTask = taskRepository.save(task);
return  savedTask;
    }
    public boolean deleteTask(Long id  ) {
          taskRepository.deleteById(id);
          return  true;
    }
}
