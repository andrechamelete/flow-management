package com.chamelete.flow_management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.Mock;

import com.chamelete.flowManagement.controller.AuthRegisterController;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.JwtUtil;
import com.chamelete.flowManagement.security.UserDetailsImpl;
import com.chamelete.flowManagement.security.dto.AuthResponse;
import com.chamelete.flowManagement.security.dto.RegisterRequest;

@ExtendWith(MockitoExtension.class)
public class AuthRegisterControllerTest {

    @InjectMocks
    private AuthRegisterController authRegisterController;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;
    
    @Test
    void register_wheEmailAlreadyExists_shouldThowException() {
        RegisterRequest authRequest = new RegisterRequest("André", "user@chamelete.com", "123456");

        when(userRepository.findByEmail(authRequest.getEmail()))
            .thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authRegisterController.register(authRequest);
        });

        assertEquals("Email já cadastrado", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_whenEmailDoesNotExists_shouldRegisterUserAndreturnJwt() {
        RegisterRequest registerRequest = new RegisterRequest("André", "andre@chamelete.com", "123456");

        when(userRepository.findByEmail(registerRequest.getEmail()))
            .thenReturn(Optional.empty());
        
        when(passwordEncoder.encode(registerRequest.getPassword()))
            .thenReturn("hashedPassword");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        when(jwtUtil.generateToken(any(UserDetailsImpl.class)))
            .thenReturn("fake-jwt-token");

        AuthResponse response = authRegisterController.register(registerRequest);
        
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("André", savedUser.getUsername());
        assertEquals("andre@chamelete.com", savedUser.getEmail());
        assertEquals("hashedPassword", savedUser.getPassword());
        assertNotNull(registerRequest);
        assertEquals("fake-jwt-token", response.getToken());
    }

    
}
