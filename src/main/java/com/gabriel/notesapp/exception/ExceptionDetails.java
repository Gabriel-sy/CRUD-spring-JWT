package com.gabriel.notesapp.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    //Mensagem padrao para as exceçoes
    protected String message;
    protected int status;
    protected String details;
    protected LocalDateTime timestamp;
}
