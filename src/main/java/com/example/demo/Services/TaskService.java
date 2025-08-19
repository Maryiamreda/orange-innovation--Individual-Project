package com.example.demo.Services;

import com.example.demo.Services.dto.TaskDto;
import com.example.demo.entities.Task;

import com.example.demo.entities.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UsersRepository;
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
        Object savedTask;
        savedTask = taskRepository.save(task);
        return  savedTask;
    }

    public boolean deleteTask(Long id  ) {
          taskRepository.deleteById(id);
          return  true;
    }
    public Task editTask(Long id , TaskDto newTask){
        Task  oldTask=taskRepository.findById(id).get();

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
            oldTask.setStatus(newTask.getStatus());
        }

return taskRepository.save(oldTask);
    }
}
