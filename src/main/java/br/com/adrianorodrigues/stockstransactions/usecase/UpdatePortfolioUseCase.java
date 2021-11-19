package br.com.adrianorodrigues.stockstransactions.usecase;

import br.com.adrianorodrigues.stockstransactions.domain.Transaction;

import java.util.List;

public interface UpdatePortfolioUseCase {

    void execute(Long userId, List<Transaction> transactions);

}
