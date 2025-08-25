package com.example.demo.entities;
import jakarta.persistence.*;
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;  // use Long instead of int

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;



    @Column(nullable = false)
    private String roles = "USER";
    // Default role
    public User() {}


    public User(String name, String password , String roles) {
        this.name = name;
        this.password = password;
      this.roles = roles;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRoles() {return roles;}
    public void setRoles(String roles) {this.roles = roles;}
}
