package com.gabriel.notesapp.repository;

import com.gabriel.notesapp.domain.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByTitle(String title);
}
