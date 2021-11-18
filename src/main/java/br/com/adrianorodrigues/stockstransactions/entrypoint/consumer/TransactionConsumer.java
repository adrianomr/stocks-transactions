package br.com.adrianorodrigues.stockstransactions.entrypoint.consumer;

import br.com.adrianorodrigues.stockstransactions.adapter.domain.TransactionAdapter;
import br.com.adrianorodrigues.stockstransactions.usecase.UpdateTransactionsUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionConsumer {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UpdateTransactionsUseCase updatePortfolio;

    @JmsListener(destination = "cei-integration.fifo")
    public void process(String message) {
        log.info(message);
        try {
            CeiUserTransactionsDto userTransactions = objectMapper
                    .readValue(message, CeiUserTransactionsDto.class);
            updatePortfolio.execute(userTransactions.getId(), TransactionAdapter
                    .fromCeiUserTransactionDto(userTransactions));
        } catch (JsonProcessingException e) {
            log.error("Error processing update-transactions.fifo queue", e);
        }
    }

}
