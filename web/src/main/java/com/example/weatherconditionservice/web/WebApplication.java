package com.example.weatherconditionservice.web;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.weatherconditionservice")
public class WebApplication
{
    @Value("${messaging.consumer.initial-size}")
    private int CONSUMER_SIZE;

    @Value("${messaging.consumer.result.auto-start}")
    private boolean CONSUMER_RESULT_AUTO_START;

    @Value("${messaging.consumer.result.max-size}")
    private int CONSUMER_RESULT_MAX_SIZE;

    @Value("${messaging.consumer.interval}")
    private Long INTERVAL_IN_MS;

    @Value("${messaging.queue.result.problem}")
    private String MESSAGING_RESULT_PROBLEM_QUEUE;

    @Value("${messaging.queue.request}")
    private String MESSAGING_REQUEST_QUEUE;

    @Value("${messaging.coord.queue.result.problem}")
    private String MESSAGING_COORD_RESULT_PROBLEM_QUEUE;

    @Value("${messaging.coord.queue.request}")
    private String MESSAGING_COORD_REQUEST_QUEUE;

    @Bean
    public AmqpTemplate resultProblemQueueTemplate(ConnectionFactory rabbitConnectionFactory,
                                                   MessageConverter messageConverter)
    {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
        template.setRoutingKey(MESSAGING_RESULT_PROBLEM_QUEUE);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public AmqpTemplate requestQueueTemplate(ConnectionFactory rabbitConnectionFactory,
                                             MessageConverter messageConverter)
    {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
        template.setRoutingKey(MESSAGING_REQUEST_QUEUE);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public AmqpTemplate coordResultProblemQueueTemplate(ConnectionFactory rabbitConnectionFactory,
                                                   MessageConverter messageConverter)
    {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
        template.setRoutingKey(MESSAGING_COORD_RESULT_PROBLEM_QUEUE);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public AmqpTemplate coordRequestQueueTemplate(ConnectionFactory rabbitConnectionFactory,
                                             MessageConverter messageConverter)
    {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
        template.setRoutingKey(MESSAGING_COORD_REQUEST_QUEUE);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory resultQueueListener(ConnectionFactory connectionFactory,
                                                                    MessageConverter messageConverter)
    {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(messageConverter);
        container.setConcurrentConsumers(CONSUMER_SIZE);
        container.setStartConsumerMinInterval(INTERVAL_IN_MS);
        container.setStopConsumerMinInterval(INTERVAL_IN_MS);
        container.setPrefetchCount(10);
        container.setMaxConcurrentConsumers(CONSUMER_RESULT_MAX_SIZE);
        container.setAutoStartup(CONSUMER_RESULT_AUTO_START);
        return container;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }

}
