package br.com.adrianorodrigues.stockstransactions.entrypoint.rest;

import br.com.adrianorodrigues.stockstransactions.external.repository.TransactionRepository;
import br.com.adrianorodrigues.stockstransactions.external.repository.dto.TransactionDto;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TransactionsControllerTest {

    @MockBean
    QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    TransactionRepository repository;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void importAcaoList() throws URISyntaxException {
        URL resource = TransactionsControllerTest.class.getClassLoader().getResource("transactions.xlsx");
        File file = Paths.get(resource.toURI()).toFile();

        given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjF9.8lSCknTnRANlJ0AVzCgO2yF838WYA7bLaAR7vAKnofo")
                .accept(ContentType.MULTIPART)
                .multiPart("file", file)
                .post("/transactions/file")
                .then()
                .statusCode(200)
                .log()
                .all();

        List<TransactionDto> transactions = repository.findAll();

        assertThat(transactions)
                .hasSize(8);

    }
}