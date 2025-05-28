package com.chamelete.flowManagement.controller;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.security.JwtUtil;
import com.chamelete.flowManagement.security.UserDetailsImpl;
import com.chamelete.flowManagement.security.dto.AuthResponse;
import com.chamelete.flowManagement.security.dto.RegisterRequest;
import com.chamelete.flowManagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/api/auth")
public class AuthRegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        User user = userService.create(request);
        
        String token = jwtUtil.generateToken(new UserDetailsImpl(user));
        return new AuthResponse(token);
    }
}
