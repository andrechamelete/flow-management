package com.chamelete.flowManagement.service;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.security.dto.RegisterRequest;

public interface UserService {
    
    User findByEmail(String email);
    
    User create(RegisterRequest request);
}