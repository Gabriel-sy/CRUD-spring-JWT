package com.gabriel.notesapp.domain.user;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(@NotEmpty String login, @NotEmpty String password) {
}
