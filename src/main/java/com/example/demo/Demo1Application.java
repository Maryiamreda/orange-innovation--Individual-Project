package com.example.demo;

import com.example.demo.entities.Task;
import com.example.demo.entities.User;
import com.example.demo.entities.TaskStatus;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaRepositories
public class Demo1Application {

    public static void main(String[] args) {

        SpringApplication.run(Demo1Application.class, args);
        System.out.println("السلام عليكم");
    }
    @Bean

    public CommandLineRunner demo(
            UsersRepository usersRepository   ,
            TaskRepository taskRepository

    ) {
        return (args) -> {
//            User user = usersRepository.findById(1L)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//            Task task = new Task(
//                    "Finish homework",
//                    "Math exercises chapter 3",
//                    TaskStatus.TODO,
//                    LocalDate.now().plusDays(2),
//                    user
//            );


//            taskRepository.save(task);
            //Non-static method 'save(S)' cannot be referenced from a static context

        };
    }

}
