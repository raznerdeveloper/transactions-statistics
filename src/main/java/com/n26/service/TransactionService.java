package com.n26.service;

import com.n26.exception.StaleTransactionException;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private static final long SIXTY_SECONDS = 60 * 1000;
    private Statistics statistics;
    private final Object lock = new Object();

    public TransactionService() {
        statistics = new Statistics();
    }

    public void addTransaction(Transaction transaction) throws StaleTransactionException {
        long sixtyMinutesAgo = System.currentTimeMillis() - SIXTY_SECONDS;
        if (sixtyMinutesAgo > transaction.getTimestamp().getTime()){
            throw new StaleTransactionException(transaction);
        }
        synchronized (lock) {
            statistics.addTransaction(transaction);
        }

    }

    public void deleteAllTransactions() {
        synchronized (lock) {
            this.statistics.reset();
        }
    }

    public Statistics getStatistics() {
        synchronized (lock) {
            return this.statistics;
        }
    }
}
