package com.chamelete.flowManagement.service.impl;

import java.util.NoSuchElementException;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.service.UserService;

public class UserServiceImpl implements UserService{
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);

    }
    
    @Override
    public User create(User userToCreate) {
        if(userRepository.existsByEmail(userToCreate.getEmail())) {
            throw new IllegalArgumentException("This Account Number already exists!");
        }

        return userRepository.save(userToCreate);
    }
}
