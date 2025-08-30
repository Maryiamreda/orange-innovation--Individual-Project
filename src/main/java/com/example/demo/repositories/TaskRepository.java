package com.example.demo.repositories;

import com.example.demo.Services.dto.TaskDto;
import com.example.demo.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByUserId(Long id);

    boolean getTasksById(Long id);
}
