package com.gabriel.notesapp.service;

import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.repository.NoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NoteServiceTest {
    @InjectMocks
    NoteService noteService;

    @Mock
    NoteRepository repository;

    @BeforeEach
    void setUp(){
        Note validNote = new Note(1L, "test", "test", "test");
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(validNote));

    }

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
    @DisplayName("createNote throws exception when note is invalid")
    void createNote_ThrowsIllegalArgumentException_WhenNoteIsInvalid() {
        Note invalidNote = new Note("blabla", "blabla", "blabla");
        BDDMockito.when(repository.save(ArgumentMatchers.any(Note.class)))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> noteService.createNote(invalidNote));

        verify(repository).save(invalidNote);
    }

    @Test
    @DisplayName("findById returns Note when successfull")
    void findById_ReturnsNote_WhenSuccessfull() {
        Note validNote = new Note(1L, "test", "test", "test");
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(validNote));
        Note noteBody = noteService.findById(1L);

        Assertions.assertThat(noteBody)
                .isNotNull()
                .isEqualTo(validNote);

       verify(repository).findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("findById throws IllegalArgumentException when id is invalid")
    void findById_ThrowsIllegalArgumentException_WhenIdIsInvalid() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> noteService.findById(1L));

        verify(repository).findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("delete deletes Note when successfull")
    void delete_DeletesNote_WhenSuccessfull() {
        Note validNote = new Note(1L, "test", "test", "test");
        Assertions.assertThatCode(() -> noteService.delete(validNote.getId()))
                .doesNotThrowAnyException();

        verify(repository).deleteById(validNote.getId());
    }

    @Test
    @DisplayName("delete throws IllegalArgumentException when id is invalid")
    void delete_ThrowsIllegalArgumentException_WhenIdIsInvalid() {
        Mockito.doThrow(IllegalArgumentException.class).when(repository).deleteById(ArgumentMatchers.anyLong());

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> noteService.delete(1L));

        verify(repository).deleteById(1L);
    }

}