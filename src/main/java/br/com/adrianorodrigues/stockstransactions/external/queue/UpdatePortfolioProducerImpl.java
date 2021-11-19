package br.com.adrianorodrigues.stockstransactions.external.queue;

import br.com.adrianorodrigues.stockstransactions.external.queue.dto.UserTransactionsDto;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdatePortfolioProducerImpl implements UpdatePortfolioProducer {

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void execute(UserTransactionsDto userTransactions) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("message-group-id", "update-portfolio");
        queueMessagingTemplate.convertAndSend("update-portfolio.fifo", userTransactions, headers);
    }
}
