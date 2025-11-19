package com.coolinaa.controller;

import com.coolinaa.entity.User;
import com.coolinaa.repository.UserRepository;
import com.coolinaa.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/v1/health")
class HealthCheck {

    private final UserService userService;
    public HealthCheck(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestParam String username) {
        User user = userService.createUser(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
