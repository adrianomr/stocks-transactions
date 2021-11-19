package br.com.adrianorodrigues.stockstransactions;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class StocksTransactionsApplicationTests {

	@MockBean
	QueueMessagingTemplate queueMessagingTemplate;

	@Test
	void contextLoads() {
	}

}
