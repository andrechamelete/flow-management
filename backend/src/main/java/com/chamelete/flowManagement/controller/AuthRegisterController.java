package com.chamelete.flowManagement.controller;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.JwtUtil;
import com.chamelete.flowManagement.security.UserDetailsImpl;
import com.chamelete.flowManagement.security.dto.AuthResponse;
import com.chamelete.flowManagement.security.dto.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthRegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);
        
        String token = jwtUtil.generateToken(new UserDetailsImpl(newUser));
        return new AuthResponse(token);
    }
}
