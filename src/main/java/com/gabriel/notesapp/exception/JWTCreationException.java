package com.gabriel.notesapp.exception;

public class JWTCreationException extends RuntimeException{
    public JWTCreationException(String message){
        super(message);
    }
}
