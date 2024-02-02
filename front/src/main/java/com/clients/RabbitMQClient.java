package com.clients;

import com.requests.ProductRequest;
import com.responses.ProductResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQClient {
    private final AmqpTemplate amqpTemplate;
    private final DirectExchange directExchange;
    private static final Logger logger = LogManager.getLogger(RabbitMQClient.class);

    @Autowired
    public RabbitMQClient(RabbitTemplate amqpTemplate, DirectExchange directExchange) {
        this.amqpTemplate = amqpTemplate;
        this.directExchange = directExchange;
    }

    public ProductResponse sendAndReceive(ProductRequest request) {
        try {
            return (ProductResponse) amqpTemplate.convertSendAndReceive(directExchange.getName(), "get.products", request);
        } catch(Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }
}