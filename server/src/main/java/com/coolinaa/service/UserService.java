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
        return userRepository.save(new User(username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
