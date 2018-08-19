package com.n26.service;

import com.n26.exception.StaleTransactionException;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.model.TransactionStore;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {

    private static final long SIXTY_SECONDS = 60 * 1000;
    private static final int BUCKET_SIZE = 60;

    private static final Map<Integer, TransactionStore> transactionBuckets = new ConcurrentHashMap<>(BUCKET_SIZE);

    public void addTransaction(Transaction transaction) throws StaleTransactionException {
        long sixtyMinutesAgo = System.currentTimeMillis() - SIXTY_SECONDS;
        if (sixtyMinutesAgo > transaction.getTimestamp().getTime()){
            throw new StaleTransactionException(transaction);
        }

        int second = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(transaction.getTimestamp().getTime()),
                ZoneId.systemDefault()
        ).getSecond();

        transactionBuckets.compute(second, (key, store) -> {
            if (store == null){
                store = new TransactionStore();
            }
            store.addTransaction(transaction);
            return store;
        });

    }

    public void deleteAllTransactions() {
        transactionBuckets.clear();
    }

    public Statistics getStatistics() {
        long sixtySecondsAgo = System.currentTimeMillis() - SIXTY_SECONDS;
        TransactionStore storeSummary = transactionBuckets.values().stream()
                .filter(transactionStore -> transactionStore.getTimestamp().getTime() > sixtySecondsAgo)
                .reduce(new TransactionStore(), (transactionStore, transactionStore2) -> {
                    transactionStore.setSum(transactionStore.getSum() + transactionStore2.getSum());
                    transactionStore.setMinimum(
                            Math.min(transactionStore.getMinimum(), transactionStore2.getMinimum())
                    );
                    transactionStore.setMaximum(
                            Math.max(transactionStore.getMaximum(), transactionStore2.getMaximum())
                    );
                    transactionStore.setCount(transactionStore.getCount() + transactionStore2.getCount());
                    return transactionStore;
                });

        return new Statistics(storeSummary);
    }
}
