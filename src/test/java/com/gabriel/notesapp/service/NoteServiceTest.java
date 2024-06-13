package com.gabriel.notesapp.service;

import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.repository.NoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NoteServiceTest {
    @InjectMocks
    NoteService noteService;

    @Mock
    NoteRepository repository;


    @Test
    @DisplayName("createNote doesnt throw excpetion when successfull")
    void createNote_DoesntThrowException_WhenSuccessfull() {
        Note validNote = new Note(1L, "test", "test", "test");
        Assertions.assertThatCode(() -> noteService.createNote(validNote))
                .doesNotThrowAnyException();

        //Verificando que o repository.save foi chamado 1 vez nesse teste, com o parametro validNoteDTO.
        verify(repository).save(validNote);
    }

    @Test
    @DisplayName("createNote throws excpetion when note is invalid")
    void createNote_ThrowsIllegalArgumentException_WhenNoteIsInvalid() {
        Note invalidNote = new Note("blabla", "blabla", "blabla");
        BDDMockito.when(repository.save(ArgumentMatchers.any(Note.class)))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> noteService.createNote(invalidNote));

        verify(repository).save(invalidNote);
    }
}