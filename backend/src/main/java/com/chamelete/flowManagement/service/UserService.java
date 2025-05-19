package com.chamelete.flowManagement.service;

import com.chamelete.flowManagement.model.User;

public interface UserService {
    
    User findByEmail(String email);
    
    User create(User userToCreate);
}