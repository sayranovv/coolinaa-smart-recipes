package com.coolinaa.service;

import com.coolinaa.entity.User;
import com.coolinaa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username) {
        User user = User.builder()
                .username(username)
                .email(username + "@example.com")
                .passwordHash("placeholder")
                .build();
        return userRepository.save(user);
    }

    public User getByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
