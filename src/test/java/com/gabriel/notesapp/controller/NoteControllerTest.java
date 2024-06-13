package com.gabriel.notesapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.notesapp.domain.note.NoteDTO;
import com.gabriel.notesapp.repository.NoteRepository;
import com.gabriel.notesapp.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NoteRepository noteRepository;
    @MockBean
    NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("createNoteApi creates note when successfull")
    void createNoteApi_CreatesNote_WhenSuccessfull() throws Exception {
        NoteDTO validDTO = new NoteDTO("test", "test", "test");
        mockMvc.perform(post("/home/api/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isOk());
    }



}