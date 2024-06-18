package com.gabriel.notesapp.service;

import com.gabriel.notesapp.domain.category.Category;
import com.gabriel.notesapp.domain.category.CategoryDTO;
import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.domain.note.NoteDTO;
import com.gabriel.notesapp.exception.EntityExistsException;
import com.gabriel.notesapp.exception.EntityNotFoundException;
import com.gabriel.notesapp.repository.CategoryRepository;
import com.gabriel.notesapp.repository.NoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NoteServiceTest {
    @InjectMocks
    NoteService noteService;

    @Mock
    NoteRepository noteRepository;
    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        Note validNote = new Note(1L, "test", "test", "test");
        BDDMockito.when(noteRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(validNote));

    }

    @Test
    @DisplayName("createNote doesnt throw excpetion when successfull")
    void createNote_DoesntThrowException_WhenSuccessfull() {
        NoteDTO validDTO = new NoteDTO("test", "test", "test");
        Assertions.assertThatCode(() -> noteService.createNote(validDTO))
                .doesNotThrowAnyException();

        //Verificando que o noteRepository.save foi chamado 1 vez nesse teste.
        verify(noteRepository).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("createNote throws exception when note is invalid")
    void createNote_ThrowsIllegalArgumentException_WhenNoteIsInvalid() {
        NoteDTO invalidDTO = new NoteDTO("blabla", "blabla", "blabla");
        BDDMockito.when(noteRepository.save(ArgumentMatchers.any(Note.class)))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> noteService.createNote(invalidDTO));

        verify(noteRepository).save(ArgumentMatchers.any(Note.class));
    }

    @Test
    @DisplayName("findById returns Note when successfull")
    void findById_ReturnsNote_WhenSuccessfull() {
        Note validNote = new Note(1L, "test", "test", "test");
        BDDMockito.when(noteRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(validNote));
        Note noteBody = noteService.findById(1L);

        Assertions.assertThat(noteBody)
                .isNotNull()
                .isEqualTo(validNote);

       verify(noteRepository).findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("findById throws IllegalArgumentException when id is invalid")
    void findById_ThrowsIllegalArgumentException_WhenIdIsInvalid() {
        BDDMockito.when(noteRepository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> noteService.findById(1L));

        verify(noteRepository).findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("delete deletes Note when successfull")
    void delete_DeletesNote_WhenSuccessfull() {
        Note validNote = new Note(1L, "test", "test", "test");
        Assertions.assertThatCode(() -> noteService.delete(validNote.getId()))
                .doesNotThrowAnyException();

        verify(noteRepository).deleteById(validNote.getId());
    }

    @Test
    @DisplayName("delete throws IllegalArgumentException when id is invalid")
    void delete_ThrowsIllegalArgumentException_WhenIdIsInvalid() {
        Mockito.doThrow(IllegalArgumentException.class).when(noteRepository).deleteById(ArgumentMatchers.anyLong());

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> noteService.delete(1L));

        verify(noteRepository).deleteById(1L);
    }

    @Test
    @DisplayName("createCategory doesnt throw exception when successfull")
    void createCategory_DoesntThrowException_WhenSuccessfull() {
        CategoryDTO validCategoryDTO = new CategoryDTO("asfas");
        Assertions.assertThatCode(() -> noteService.createCategory(validCategoryDTO))
                        .doesNotThrowAnyException();

        verify(categoryRepository).findByCategoryName(ArgumentMatchers.any());
        verify(categoryRepository).save(ArgumentMatchers.any(Category.class));
    }

    @Test
    @DisplayName("createCategory throws exception when category already exists")
    void createCategory_ThrowsException_WhenCategoryAlreadyExists() {
        CategoryDTO validDTO = new CategoryDTO("blabal");

        Mockito.doThrow(EntityExistsException.class).when(categoryRepository)
                .findByCategoryName(ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(EntityExistsException.class)
                .isThrownBy(() -> noteService.createCategory(validDTO));

        verify(categoryRepository).findByCategoryName(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("deleteCategory doesnt throw exception when successfull")
    void deleteCategory_DoesntThrowException_WhenSuccessfull() {
        BDDMockito.when(categoryRepository.existsById(ArgumentMatchers.anyLong()))
                        .thenReturn(true);
        Assertions.assertThatCode(() -> noteService.deleteCategory(1L))
                .doesNotThrowAnyException();

        verify(categoryRepository).existsById(1L);
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteCategory throws exception when category doesnt exist")
    void deleteCategory_ThrowsException_WhenCategoryDoesntExist() {

        Mockito.doThrow(EntityNotFoundException.class).when(categoryRepository)
                .existsById(ArgumentMatchers.anyLong());

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> noteService.deleteCategory(1L));

        verify(categoryRepository).existsById(1L);
        verify(categoryRepository, never()).deleteById(1L);
    }



}