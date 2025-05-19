package com.chamelete.flow_management.controller;

import com.chamelete.flowManagement.FlowManagementApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest(classes = FlowManagementApplication.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {
        String jsonRequest = "{\"email\":\"marcelo@email.com\", \"password\":\"102030\"}";

        mockMvc.perform(post("/api/auth/login")
            .contentType("application/json")
            .content(jsonRequest))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.jwt").exists());
    }

}

