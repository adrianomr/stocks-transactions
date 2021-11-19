package br.com.adrianorodrigues.stockstransactions.config;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import javax.jms.Session;

@Configuration
@EnableJms
@Profile({"!test"})
public class JmsConfig {

    @Value("${amazon.accessKey}")
    private String accessKey;
    @Value("${amazon.secretKey}")
    private String secretKey;

    @Autowired
    private SQSConnectionFactory connectionFactory;

    @Bean
    public SQSConnectionFactory createConnectionFactory() {
        return SQSConnectionFactory.builder()
                .withRegion(Region.getRegion(Regions.SA_EAST_1))
                .withAWSCredentialsProvider(new StaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);

        return factory;
    }

    @Bean
    public QueueMessagingTemplate defaultJmsTemplate() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        QueueMessagingTemplate queueMessagingTemplate = new QueueMessagingTemplate(AmazonSQSAsyncClientBuilder
                .standard()
                .withRegion(Regions.SA_EAST_1.getName())
                .withCredentials(new StaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build());
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(mapper);
        messageConverter.setStrictContentTypeMatch(false);
        queueMessagingTemplate.setMessageConverter(messageConverter);
        return queueMessagingTemplate;
    }

}