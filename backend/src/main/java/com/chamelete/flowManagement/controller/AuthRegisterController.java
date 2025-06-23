package com.chamelete.flowManagement.controller;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.security.JwtUtil;
import com.chamelete.flowManagement.security.UserDetailsImpl;
import com.chamelete.flowManagement.security.dto.AuthResponse;
import com.chamelete.flowManagement.security.dto.RegisterRequest;
import com.chamelete.flowManagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/auth")
public class AuthRegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try {
            User user = userService.create(request);
        
            String token = jwtUtil.generateToken(new UserDetailsImpl(user));
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered by another user");
        }
        
    }
}
