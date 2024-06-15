package com.gabriel.notesapp.domain.note;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "note")
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String noteCategory;

    public Note(NoteDTO data){
        this.title = data.title();
        this.content = data.content();
        this.noteCategory = data.noteCategory();
    }

    public Note(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.noteCategory = category;
    }
}
