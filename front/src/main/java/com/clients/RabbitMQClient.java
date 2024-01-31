package com.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.requests.ProductRequest;
import com.responses.ProductResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQClient {
    private final AmqpTemplate amqpTemplate;
    private ObjectMapper mapper;
    private final DirectExchange directExchange;

    @Autowired
    public RabbitMQClient(RabbitTemplate amqpTemplate, ObjectMapper mapper, DirectExchange directExchange) {
        this.amqpTemplate = amqpTemplate;
        this.directExchange = directExchange;
        this.mapper = mapper;
    }

    public ProductResponse sendAndReceive(ProductRequest request) {
        try {
            return (ProductResponse) amqpTemplate.convertSendAndReceive(directExchange.getName(), "get.products", request);
        } catch(Exception ex) {
            return null;
        }
    }
}