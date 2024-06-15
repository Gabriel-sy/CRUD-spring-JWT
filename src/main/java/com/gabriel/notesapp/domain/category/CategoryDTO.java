package com.gabriel.notesapp.domain.category;

import jakarta.validation.constraints.NotEmpty;

public record CategoryDTO(@NotEmpty String categoryName) {
}
