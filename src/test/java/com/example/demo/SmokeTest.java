package com.example.demo;


import com.example.demo.Controllers.AuthController;
import com.example.demo.Controllers.TaskControllers;
import com.example.demo.Controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
//@SpringBootTest will try to wire up your controllers and endpoints
//the same way it does when the app runs.
@SpringBootTest

public class SmokeTest {
    @Autowired
    private UserController userController;
    @Autowired
    private TaskControllers taskControllers;
    @Autowired
    private AuthController authController;
    @Test
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
        assertThat(taskControllers).isNotNull();
        assertThat(authController).isNotNull();

    }


}
