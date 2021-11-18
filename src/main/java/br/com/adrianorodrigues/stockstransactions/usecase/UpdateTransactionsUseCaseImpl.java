package br.com.adrianorodrigues.stockstransactions.usecase;

import br.com.adrianorodrigues.stockstransactions.adapter.domain.TransactionAdapter;
import br.com.adrianorodrigues.stockstransactions.adapter.external.repository.TransactionDtoAdapter;
import br.com.adrianorodrigues.stockstransactions.domain.Transaction;
import br.com.adrianorodrigues.stockstransactions.domain.UserTransactions;
import br.com.adrianorodrigues.stockstransactions.external.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UpdateTransactionsUseCaseImpl implements UpdateTransactionsUseCase {

    @Autowired
    TransactionRepository repository;

    @Override
    public void execute(Long userId, List<Transaction> transactions) {
        log.info("user: {} transactions: {}", userId, transactions);

        List<Transaction> persistedTransactions = TransactionAdapter
                .fromTransactionDto(repository.findAllByUserId(userId));

        UserTransactions userTransactions = new UserTransactions(persistedTransactions);

        List<Transaction> newTransactions = userTransactions.findNewTransactions(transactions);

        repository.saveAll(TransactionDtoAdapter.INSTANCE.convert(newTransactions));
    }

}
