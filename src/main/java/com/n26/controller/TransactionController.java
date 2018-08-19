package com.n26.controller;

import com.n26.model.Transaction;
import com.n26.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private TransactionService transactionService;

    public TransactionController (@Autowired TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody @Valid Transaction transaction) {
        transactionService.addTransaction(transaction);
        LOGGER.info("Added new transaction: " + transaction);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTransactions() {
        transactionService.deleteAllTransactions();
        LOGGER.info("Deleted all transactions");
    }
}
