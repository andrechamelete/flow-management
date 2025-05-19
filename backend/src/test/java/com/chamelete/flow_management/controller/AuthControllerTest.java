package com.chamelete.flow_management.controller;

import com.chamelete.flowManagement.controller.AuthController;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.security.JwtUtil;
import com.chamelete.flowManagement.security.UserDetailsImpl;
import com.chamelete.flowManagement.security.dto.AuthRequest;
import com.chamelete.flowManagement.security.dto.AuthResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.security.core.Authentication;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @Test
    void login_withValidCredentials_shouldReturnJwt() {

        AuthRequest authRequest = new AuthRequest("user@chamelete.com", "123456");

        User user = new User();
        user.setId(1L);
        user.setEmail("user@chamelete.com");
        user.setPassword("123456");

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);

        when(authenticationManager.authenticate(any()))
            .thenReturn(authentication);

        when(authentication.getPrincipal())
            .thenReturn(userDetailsImpl);

        when(jwtUtil.generateToken(userDetailsImpl))
            .thenReturn("fake-jwt-token");

        AuthResponse authResponse = authController.login(authRequest);

        assertNotNull(authResponse);
        assertEquals("fake-jwt-token", authResponse.getToken());
    }

    @Test
    void login_withInvalidCredentials_shouldThrowRuntimeException() {
        AuthRequest authRequest = new AuthRequest("invalid@chamelete.com", "wrong");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authController.login(authRequest);
        });
        
        assertEquals("Invalid credentials", thrown.getMessage());
    }
        
    
}

