package com.n26.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
public class Transaction {

    public Transaction() {
    }

    public Transaction(@NotNull Double amount, @NotNull @PastOrPresent Date timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @NotNull
    private Double amount;

    @NotNull
    @PastOrPresent
    private Date timestamp;

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
