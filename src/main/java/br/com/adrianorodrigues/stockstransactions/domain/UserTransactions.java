package br.com.adrianorodrigues.stockstransactions.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class UserTransactions {

    private List<Transaction> transactions;

    public UserTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> findNewTransactions(List<Transaction> transactions) {
        Map<String, Integer> transactionsPerDay = new HashMap<>();

        return transactions
                .stream()
                .filter(transaction -> notExistsTransaction(transactionsPerDay, transaction))
                .collect(Collectors.toList());
    }

    private boolean notExistsTransaction(Map<String, Integer> transactionsPerDay, Transaction transaction) {
        int countTransactionsPerDay = transactionsPerDay.getOrDefault(transaction.generateKey(), 0);
        countTransactionsPerDay++;
        transactionsPerDay.put(transaction.generateKey(), countTransactionsPerDay);
        transaction.setSequence(countTransactionsPerDay);
        return this
                .transactions
                .stream()
                .noneMatch(transaction1 -> transaction1.equalsIgnoringId(transaction));
    }

}
