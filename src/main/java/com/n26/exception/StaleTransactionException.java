package com.n26.exception;

import com.n26.model.Transaction;
import lombok.Getter;

public class StaleTransactionException extends RuntimeException {

    @Getter
    private Transaction transaction;

    public StaleTransactionException(Transaction transaction){
        super();
        this.transaction = transaction;
    }
}
