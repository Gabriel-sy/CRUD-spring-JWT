package com.gabriel.notesapp.service;

import com.gabriel.notesapp.domain.category.Category;
import com.gabriel.notesapp.domain.category.CategoryDTO;
import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.exception.EntityExistsException;
import com.gabriel.notesapp.repository.CategoryRepository;
import com.gabriel.notesapp.repository.NoteRepository;
import com.gabriel.notesapp.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public NoteService(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }



    //Salvando nota no banco
    public void createNote(Note note){
        if (noteRepository.findByTitle(note.getTitle()).isEmpty()){
            noteRepository.save(note);
        } else {
            throw new EntityExistsException("Esse titulo já existe");
        }

    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("ID nao encontrado"));
    }

    public void delete(Long id) {
        if (noteRepository.findById(id).isPresent()){
            noteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("ID nao encontrado");
        }
    }

    public void createCategory(CategoryDTO categoryDTO) {
        Category newCategory = new Category(categoryDTO);
        if (categoryRepository.findByCategoryName(newCategory.getCategoryName()).isEmpty()){
            categoryRepository.save(newCategory);
        } else {
            throw new EntityExistsException("Categoria já existe");
        }
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Categoria nao encontrada");
        }
    }
}
