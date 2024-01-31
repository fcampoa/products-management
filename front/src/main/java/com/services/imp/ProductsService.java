package com.services.imp;

import com.clients.RabbitMQClient;
import com.domain.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.requests.ProductRequest;
import com.responses.ProductResponse;
import com.services.IProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductsService implements IProductsService {
    private final RabbitMQClient rabbitMQClient;
    private final ObjectMapper mapper;
    public ProductsService(RabbitMQClient rabbitMQClient, ObjectMapper mapper) {
        this.rabbitMQClient = rabbitMQClient;
        this.mapper = mapper;
    }
    @Override
    public List<Product> getProductsList() throws Exception {
        try {
            ProductRequest request = new ProductRequest("products.getAll", "get", "GET");
            ProductResponse response = rabbitMQClient.sendAndReceive(request);
            HttpStatus status = HttpStatus.valueOf(response.getResponseStatus());
            if (!status.is2xxSuccessful()) {
                throw new Exception(response.getContent());
            }
            return mapper.readValue(response.getContent(), new TypeReference<List<Product>>() {
            });
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public int addProduct(Product product) throws Exception {
        ProductRequest request = new ProductRequest("products.add", mapper.writeValueAsString(product), "POST");
        ProductResponse response = rabbitMQClient.sendAndReceive(request);
        HttpStatus status = HttpStatus.valueOf(response.getResponseStatus());
        if (status.is5xxServerError()) {
            throw new Exception(response.getContent());
        }
        return status.is2xxSuccessful() ? 1 : 0;
    }
}

