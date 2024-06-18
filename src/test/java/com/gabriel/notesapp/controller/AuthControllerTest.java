package com.gabriel.notesapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.notesapp.domain.user.UserDTO;
import com.gabriel.notesapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("registerApi returns OK when user is valid")
    void registerApi_ReturnsOk_WhenUserIsValid() throws Exception {
        UserDTO validDTO = new UserDTO("test", "test");
        mockMvc.perform(post("/auth/api/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isOk());

        verify(userService).register(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("registerApi returns bad request when format is invalid")
    void registerApi_ReturnsBadRequest_WhenFormatIsInvalid() throws Exception {
        mockMvc.perform(post("/auth/api/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("test")))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("loginApi returns OK when user is valid")
    void loginApi_ReturnsOk_WhenUserIsValid() throws Exception {
        UserDTO validDTO = new UserDTO("test", "test");
        mockMvc.perform(post("/auth/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isOk());

        verify(userService).login(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    @DisplayName("loginApi returns bad request when user or format is invalid")
    void loginApi_ReturnsBadRequest_WhenUserOrFormatIsInvalid() throws Exception {
        mockMvc.perform(post("/auth/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("test")))
                .andExpect(status().isBadRequest());

        verify(userService, never()).login(ArgumentMatchers.any(), ArgumentMatchers.any());
    }
}