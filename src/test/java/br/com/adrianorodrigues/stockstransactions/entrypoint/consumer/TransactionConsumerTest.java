package br.com.adrianorodrigues.stockstransactions.entrypoint.consumer;

import br.com.adrianorodrigues.stockstransactions.enums.TransactionOrigin;
import br.com.adrianorodrigues.stockstransactions.external.repository.TransactionRepository;
import br.com.adrianorodrigues.stockstransactions.external.repository.dto.TransactionDto;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TransactionConsumerTest {

    @Autowired
    TransactionConsumer transactionConsumer;
    @Autowired
    TransactionRepository repository;
    @MockBean
    QueueMessagingTemplate queueMessagingTemplate;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void whenTransactionReceivedShouldInsertTransaction() {
        String message = "{ \"id\": 1, \"transactions\": [{\"operation_date\": \"2020-06-22\", \"action\": \"buy\", \"market_type\": \"unit\", \"raw_negotiation_code\": \"HGRE11\", \"asset_specification\": \"FII HG REAL CI\", \"unit_amount\": 1, \"unit_price\": \"149.95\", \"total_price\": \"149.95\", \"quotation_factor\": 1}]}";

        transactionConsumer.process(message);

        List<TransactionDto> transactions = repository.findAll();

        assertEquals(1, transactions.size());
    }

    @Test
    void whenTransactionReceivedTwiceShouldInsertOnlyOnce() {
        String message = "{ \"id\": 1, \"transactions\": [{\"operation_date\": \"2020-06-22\", \"action\": \"buy\", \"market_type\": \"unit\", \"raw_negotiation_code\": \"HGRE11\", \"asset_specification\": \"FII HG REAL CI\", \"unit_amount\": 1, \"unit_price\": \"149.95\", \"total_price\": \"149.95\", \"quotation_factor\": 1}]}";

        transactionConsumer.process(message);

        transactionConsumer.process(message);

        List<TransactionDto> transactions = repository.findAll();

        assertEquals(1, transactions.size());
    }

    @Test
    void whenThreeTransactionsReceivedShouldInsertTransactions() {
        String message = "{ \"id\": 1, \"transactions\": [{\"operation_date\": \"2020-06-22\", \"action\": \"buy\", \"market_type\": \"unit\", \"raw_negotiation_code\": \"HGRE11\", \"asset_specification\": \"FII HG REAL CI\", \"unit_amount\": 1, \"unit_price\": \"149.95\", \"total_price\": \"149.95\", \"quotation_factor\": 1}, {\"operation_date\": \"2020-06-22\", \"action\": \"buy\", \"market_type\": \"unit\", \"raw_negotiation_code\": \"HGRE11\", \"asset_specification\": \"FII HG REAL CI\", \"unit_amount\": 1, \"unit_price\": \"149.95\", \"total_price\": \"149.95\", \"quotation_factor\": 1}, {\"operation_date\": \"2020-06-22\", \"action\": \"buy\", \"market_type\": \"unit\", \"raw_negotiation_code\": \"XPLG11\", \"asset_specification\": \"XPLG11\", \"unit_amount\": 1, \"unit_price\": \"149.95\", \"total_price\": \"149.95\", \"quotation_factor\": 1}]}";

        transactionConsumer.process(message);

        transactionConsumer.process(message);

        List<TransactionDto> transactions = repository.findAll();

        assertEquals(3, transactions.size());
    }

    @Test
    void whenTransactionInsertedShouldAddCEI_B3AsTransactionOrigin() {
        String message = "{ \"id\": 1, \"transactions\": [{\"operation_date\": \"2020-06-22\", \"action\": \"buy\", \"market_type\": \"unit\", \"raw_negotiation_code\": \"HGRE11\", \"asset_specification\": \"FII HG REAL CI\", \"unit_amount\": 1, \"unit_price\": \"149.95\", \"total_price\": \"149.95\", \"quotation_factor\": 1}]}";

        transactionConsumer.process(message);

        List<TransactionDto> transactions = repository.findAll();

        assertEquals(TransactionOrigin.CEI_B3, transactions.get(0).getOrigin());
    }

}