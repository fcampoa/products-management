package com.rabbit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitMQConfig {
    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";
    public static final String PRODUCTS_QUEUE = "products";
    public static final String DLX_EXCHANGE_MESSAGES = PRODUCTS_QUEUE + ".dlx";
    public static final String DIRECT_EXCHANGE_ROUTING = "get.products";
    @Bean
    public Queue productsQueue() {
        return QueueBuilder.durable(PRODUCTS_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .build();
    }
    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX_EXCHANGE_MESSAGES);
    }
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(PRODUCTS_QUEUE);
    }
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(productsQueue()).to(directExchange()).with(DIRECT_EXCHANGE_ROUTING);
    }
    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory();
    }
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(CachingConnectionFactory connectionFactory, TaskExecutor executor){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(PRODUCTS_QUEUE);
        container.setTaskExecutor(executor);
        return container;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(CachingConnectionFactory connectionFactory, ErrorHandler errorHandler, Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(errorHandler);
        factory.setMessageConverter(converter);
        return factory;
    }
    @Bean
    public ErrorHandler customErrorHandler() {
        return new CustomErrorHandler();
    }
}
