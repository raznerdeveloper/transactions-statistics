package com.n26.controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.n26.exception.StaleTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionProcessor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public void invalidRequestBody(){
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void invalidJson(){
    }

    @ExceptionHandler(MismatchedInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleAnyException(){
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public void invalidFormat(){
    }

    @ExceptionHandler(StaleTransactionException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void staleTransaction(){
    }
}
