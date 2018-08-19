package com.n26.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionStore {

    private Double sum;

    private Double maximum;

    private Double minimum;

    private long count;

    private Date timestamp;

    public TransactionStore() {
        this.sum = 0.0;
        this.maximum = Double.MIN_VALUE;
        this.minimum = Double.MAX_VALUE;
        this.count = 0;
    }

    public void addTransaction(Transaction transaction) {
        Double amount = transaction.getAmount();
        this.minimum = Math.min(transaction.getAmount(), this.minimum);
        this.maximum = Math.max(transaction.getAmount(), this.maximum);
        this.sum += amount;
        this.count += 1;
        this.timestamp = transaction.getTimestamp();
    }
}
