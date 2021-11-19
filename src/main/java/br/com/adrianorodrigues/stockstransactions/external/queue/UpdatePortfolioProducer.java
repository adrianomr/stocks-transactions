package br.com.adrianorodrigues.stockstransactions.external.queue;

import br.com.adrianorodrigues.stockstransactions.external.queue.dto.UserTransactionsDto;

public interface UpdatePortfolioProducer {
    void execute(UserTransactionsDto userTransactions);
}
