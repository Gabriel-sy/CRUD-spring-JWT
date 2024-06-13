package com.gabriel.notesapp.service;

import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.repository.NoteRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }



    //Salvando nota no banco
    public void createNote(Note note){
        if (noteRepository.findByTitle(note.getTitle()).isEmpty()){
            noteRepository.save(note);
        } else {
            throw new IllegalArgumentException();
        }

    }
}
