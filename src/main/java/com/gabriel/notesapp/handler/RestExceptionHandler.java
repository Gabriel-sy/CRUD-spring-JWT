package com.gabriel.notesapp.handler;

import com.gabriel.notesapp.exception.EntityExistsException;
import com.gabriel.notesapp.exception.EntityExistsExceptionDetails;
import com.gabriel.notesapp.exception.EntityNotFoundException;
import com.gabriel.notesapp.exception.EntityNotFoundExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<EntityExistsExceptionDetails> entityExistsExceptionHandler(EntityExistsException exception){
        return new ResponseEntity<>(
                    EntityExistsExceptionDetails.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .details("Entidade já existe")
                            .timestamp(LocalDateTime.now())
                            .build(), HttpStatus.BAD_REQUEST
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EntityNotFoundExceptionDetails> entityNotFoundExceptionHandler(EntityNotFoundException exception){
        return new ResponseEntity<>(
                EntityNotFoundExceptionDetails.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .details("Entidade nao encontrada")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND
                );
    }
}
