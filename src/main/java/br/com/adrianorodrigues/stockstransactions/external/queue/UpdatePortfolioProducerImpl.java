package br.com.adrianorodrigues.stockstransactions.external.queue;

import br.com.adrianorodrigues.stockstransactions.external.queue.dto.UserTransactionsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdatePortfolioProducerImpl implements UpdatePortfolioProducer {

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Value("external.queue.updatePortfolio.name")
    String queueName;

    @Override
    public void execute(UserTransactionsDto userTransactions) {
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("message-group-id", "update-portfolio");
            String message = objectMapper.writeValueAsString(userTransactions);
            queueMessagingTemplate.convertAndSend(queueName, message, headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("unable to process user transactions", e);
        }
    }
}
