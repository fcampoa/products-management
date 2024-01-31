package com.rabbit;

import com.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.requests.ProductRequest;
import com.responses.ProductResponse;
import com.services.IProductsService;
import com.utilities.Actions;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RabbitListeners {
    private final IProductsService productsService;
    private final ObjectMapper mapper;

    @Autowired
    public RabbitListeners(IProductsService productsService, ObjectMapper mapper) {
        this.productsService = productsService;
        this.mapper = mapper;
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCTS_QUEUE)
    public synchronized ProductResponse processRequest(@Payload ProductRequest request) {
        // Process the request and prepare the response
        ProductResponse response = new ProductResponse();
        Actions action = Actions.getFromValue(request.getRequestId());
        try {
            response.setRequestId(request.getRequestId());
            switch(Objects.requireNonNull(action)) {
                case GETLIST:
                    response.setContent(mapper.writeValueAsString(productsService.getProducts()));
                    response.setResponseStatus(HttpStatus.OK.value());
                    break;
                case ADD:
                    addProduct(request, response);
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex) {
            response.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContent(ex.getMessage());
        }
        return response;
    }
    private void addProduct(ProductRequest request, ProductResponse response) throws Exception {
        try {
            Product p = mapper.readValue(request.getContent(), Product.class);
            response.setContent(String.valueOf(productsService.addProduct(p)));
            response.setResponseStatus(HttpStatus.CREATED.value());
        }
        catch(Exception ex) {
            throw new Exception(ex);
        }
    }
}
