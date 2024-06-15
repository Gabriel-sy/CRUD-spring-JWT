package com.gabriel.notesapp.domain.user;

import jakarta.validation.constraints.NotEmpty;

public record UserDTO(@NotEmpty String login, @NotEmpty String password, UserRoles role) {
}
