package com.gabriel.notesapp.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
public class MethodArgumentNotValidExceptionDetails extends ExceptionDetails{
    protected String field;
    protected String fieldMessage;
}
