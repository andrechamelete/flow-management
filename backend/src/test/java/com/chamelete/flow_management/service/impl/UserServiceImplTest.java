package com.chamelete.flow_management.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.service.impl.UserServiceImpl;
import com.chamelete.flowManagement.security.dto.RegisterRequest;;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void createUser_whenEmailNotExists_shouldSaveuser() {
        String email = "teste@chamelete.com";
        RegisterRequest register = new RegisterRequest("nome", email, "pass");

        User createdUser = userServiceImpl.create(register);

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(createdUser)).thenReturn(createdUser);

        assertEquals(email, createdUser.getEmail());
        assertNotNull(createdUser);
    }

    @Test
    void createUser_whenEmailsExists_shouldThrowException() {
        User userToCreate = new User();
        userToCreate.setEmail("existe@chamelete.com");

        when(userRepository.existsByEmail(userToCreate.getEmail())).thenReturn(true);

        RegisterRequest request = new RegisterRequest("nome", userToCreate.getEmail(), "pass");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.create(request);
        });

        assertEquals("This Account Number already exists!", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void findByEmail_whenExists_shouldReturnUser() {
        String email = "existente@chamelete.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User found = userServiceImpl.findByEmail(email);

        assertNotNull(found);
        assertEquals(email, found.getEmail());
    }

    @Test
    void findByEmail_whenNotExists_shouldThrowsException() {
        String email = "naoexiste@chamelete.com";

        lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userServiceImpl.findByEmail(email);
        });
    }

}
