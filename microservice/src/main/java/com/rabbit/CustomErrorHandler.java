package com.rabbit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ErrorHandler;

public class CustomErrorHandler implements ErrorHandler {
    private static final Logger logger = LogManager.getLogger(CustomErrorHandler.class);
    @Override
    public void handleError(Throwable t) {
        logger.error(t.getMessage());
    }
}
