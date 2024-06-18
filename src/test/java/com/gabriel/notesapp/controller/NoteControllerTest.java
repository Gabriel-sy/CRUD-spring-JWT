package com.gabriel.notesapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.domain.note.NoteDTO;
import com.gabriel.notesapp.repository.NoteRepository;
import com.gabriel.notesapp.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;




    @MockBean
    NoteRepository noteRepository;
    @MockBean
    NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("createNoteApi creates note when successfull")
    @WithMockUser
    void createNoteApi_CreatesNote_WhenSuccessfull() throws Exception {
        NoteDTO validDTO = new NoteDTO("test", "test", "test");
        mockMvc.perform(post("/home/api/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isOk());

        verify(noteService).createNote(ArgumentMatchers.any(NoteDTO.class));
    }


    @Test
    @DisplayName("replaceApi edits note when successfull")
    @WithMockUser
    void replaceApi_EditsNote_WhenSuccessfull() throws Exception {
        Note validNote = new Note(1L, "test", "test", "test");
        mockMvc.perform(put("/home/api/edit").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validNote)))
                .andExpect(status().isOk());

        verify(noteService).editNote(ArgumentMatchers.any(Note.class));
    }

    @Test
    @DisplayName("deleteApi deletes note when successfull")
    @WithMockUser
    void deleteApi_deletesNote_WhenSuccessfull() throws Exception {
        long id = 1L;

        doNothing().when(noteService).delete(ArgumentMatchers.anyLong());

        mockMvc.perform(post("/home/api/delete/" + id))
                .andExpect(status().isOk());

        verify(noteService).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("createNoteApi returns unauthorized when user is not sent")
    void createNoteApi_ReturnsUnauthorized_WhenNoUserIsSent() throws Exception {
        NoteDTO validDTO = new NoteDTO("test", "test", "test");
        mockMvc.perform(post("/home/api/create").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isUnauthorized());

        verify(noteService, never()).createNote(ArgumentMatchers.any(NoteDTO.class));
    }

    @Test
    @DisplayName("replaceApi returns unauthorized when user is not sent")
    void replaceApi_ReturnsUnauthorized_WhenNoUserIsSent() throws Exception {
        Note validNote = new Note(1L, "test", "test", "test");
        mockMvc.perform(put("/home/api/edit").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validNote)))
                .andExpect(status().isUnauthorized());

        verify(noteService, never()).editNote(ArgumentMatchers.any(Note.class));
    }

    @Test
    @DisplayName("deleteApi returns unauthorized when user is not sent")
    void deleteApi_ReturnsUnauthorized_WhenNoUserIsSent() throws Exception {
        long id = 1L;

        doNothing().when(noteService).delete(ArgumentMatchers.anyLong());

        mockMvc.perform(post("/home/api/delete/" + id))
                .andExpect(status().isUnauthorized());

        verify(noteService, never()).delete(ArgumentMatchers.anyLong());
    }



}