package com.chamelete.flow_management.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void createUser_whenEmailNotExists_shouldSaveuser() {
        String email = "teste@chamelete.com";
        User userToCreate = new User();
        userToCreate.setEmail(email);

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(userToCreate)).thenReturn(userToCreate);

        User createdUser = userServiceImpl.create(userToCreate);

        assertNotNull(createdUser);
        assertEquals(email, createdUser.getEmail());
        /* 
        assertNotNull(createdUser);
        assertEquals("testes@chamelete.com", createdUser.getEmail());
        verify(userRepository).save(userToCreate);*/
    }

    @Test
    void createUser_whenEmailsExists_shouldThrowException() {
        User userToCreate = new User();
        userToCreate.setEmail("existe@chamelete.com");

        when(userRepository.existsByEmail(userToCreate.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.create(userToCreate);
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
