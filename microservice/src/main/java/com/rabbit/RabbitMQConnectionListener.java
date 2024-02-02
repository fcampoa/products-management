package com.rabbit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.listener.ListenerContainerConsumerFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnectionListener implements ApplicationListener<ListenerContainerConsumerFailedEvent> {
    private static final Logger logger = LogManager.getLogger(CustomErrorHandler.class);
    @Override
    public void onApplicationEvent(ListenerContainerConsumerFailedEvent event) {
        // Handle the event, log, and take necessary actions.
        logger.error(event.getThrowable().getMessage());
    }
}
