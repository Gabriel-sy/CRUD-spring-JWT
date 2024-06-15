package com.gabriel.notesapp.domain.user;

public enum UserRoles {
    USER("user"),
    ADMIN("admin");

    private final String role;

    UserRoles(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }
}
