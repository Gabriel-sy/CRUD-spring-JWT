package com.gabriel.notesapp.handler;

import com.gabriel.notesapp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<JWTCreationExceptionDetails> jWTCreationExceptionHandler(JWTCreationException exception){
        return new ResponseEntity<>(
                JWTCreationExceptionDetails.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .details("ERRO AO CRIAR TOKEN")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        //Pegando quais campos estavam com problema
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        //Pegando quais foram os erros
        String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                MethodArgumentNotValidExceptionDetails.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details("Formato inválido")
                        .field(fields)
                        .fieldMessage(fieldMessage)
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<InternalAuthenticationServiceExceptionDetails> internalAuthenticationServiceExceptionHandler(InternalAuthenticationServiceException exception){
        return new ResponseEntity<>(
                InternalAuthenticationServiceExceptionDetails.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details("Erro ao autenticar, tente novamente")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}
