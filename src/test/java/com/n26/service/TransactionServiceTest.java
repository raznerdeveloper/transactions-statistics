package com.n26.service;

import com.n26.exception.StaleTransactionException;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    @Test
    public void addTransaction() {
        TransactionService service = new TransactionService();
        service.addTransaction(new Transaction(123.465, new Date()));
        service.addTransaction(new Transaction(
                0.465,
                new Date(Instant.now().minusSeconds(40).getEpochSecond() * 1000))
        );

        Statistics statistics = service.getStatistics();
        assertEquals(2, statistics.getCount());
        assertEquals(BigDecimal.valueOf(123.93), statistics.getSum());
        assertEquals(BigDecimal.valueOf(123.465), statistics.getMaximum());
        assertEquals(BigDecimal.valueOf(0.465), statistics.getMinimum());

    }

    @Test(expected = StaleTransactionException.class)
    public void addStaleTransaction() {
        TransactionService service = new TransactionService();
        service.addTransaction(new Transaction(
                0.465, new Date(Instant.now().minusSeconds(120).getEpochSecond() * 1000))
        );

    }

    @Test
    public void deleteAllTransactions() {
        TransactionService service = new TransactionService();
        service.addTransaction(new Transaction(123.465, new Date()));
        service.deleteAllTransactions();

        Statistics statistics = service.getStatistics();
        assertEquals(0, statistics.getCount());
        assertEquals(BigDecimal.valueOf(0.0), statistics.getSum());
        assertEquals(BigDecimal.valueOf(0), statistics.getMaximum());
        assertEquals(BigDecimal.valueOf(0), statistics.getMinimum());
    }

    @Test
    public void getStatistics() {
    }
}