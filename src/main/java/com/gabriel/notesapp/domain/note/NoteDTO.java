package com.gabriel.notesapp.domain.note;

import jakarta.validation.constraints.NotEmpty;

public record NoteDTO(@NotEmpty String title, @NotEmpty String content, @NotEmpty String noteCategory) {
}
