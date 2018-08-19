package com.n26.controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.n26.exception.StaleTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionProcessor.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public void invalidRequestBody(MethodArgumentNotValidException ex){
        LOGGER.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void invalidJson(JsonParseException ex){
        LOGGER.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(MismatchedInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleAnyException(MismatchedInputException ex){
        LOGGER.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public void invalidFormat(InvalidFormatException ex){
        LOGGER.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(StaleTransactionException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void staleTransaction(StaleTransactionException ex){
        LOGGER.error("The transaction " + ex.getTransaction() + " is considered stale");
    }
}
