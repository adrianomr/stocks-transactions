package br.com.adrianorodrigues.stockstransactions.usecase;

import br.com.adrianorodrigues.stockstransactions.adapter.external.queue.StockTransactionDtoAdapter;
import br.com.adrianorodrigues.stockstransactions.domain.Transaction;
import br.com.adrianorodrigues.stockstransactions.external.queue.UpdatePortfolioProducer;
import br.com.adrianorodrigues.stockstransactions.external.queue.dto.UserTransactionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdatePortfolioUseCaseImpl implements UpdatePortfolioUseCase{

    @Autowired
    private UpdatePortfolioProducer producer;

    @Override
    public void execute(Long userId, List<Transaction> transactions) {
        UserTransactionsDto userTransactions = UserTransactionsDto
                .builder()
                .id(userId)
                .transactions(StockTransactionDtoAdapter.INSTANCE.convert(transactions))
                .build();

        producer.execute(userTransactions);
    }

}
