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
    public ResponseEntity<ExceptionDetails> entityExistsExceptionHandler(EntityExistsException exception){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .details("Entidade já existe")
                            .timestamp(LocalDateTime.now())
                            .build(), HttpStatus.BAD_REQUEST
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDetails> entityNotFoundExceptionHandler(EntityNotFoundException exception){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .details("Entidade nao encontrada")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND
                );
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ExceptionDetails> jWTCreationExceptionHandler(JWTCreationException exception){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
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
        //O map vai passar por todos os elementos da lista e aplicar a funçao passada e retorna cada elemento com a
        // transformaçao da funçao na mesma ordem, nesse fieldMessage, ele vai retornar a lista "fieldErrors",
        // mas com todos os elementos substituidos pela getDefaultMessage
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
    public ResponseEntity<ExceptionDetails> internalAuthenticationServiceExceptionHandler(InternalAuthenticationServiceException exception){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details("Erro ao autenticar, tente novamente")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}
