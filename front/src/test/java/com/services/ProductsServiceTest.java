package com.services;

import com.clients.RabbitMQClient;
import com.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.requests.ProductRequest;
import com.responses.ProductResponse;
import com.services.imp.ProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest {
    private IProductsService subject;
    @Mock
    RabbitMQClient client;

    ObjectMapper mapper = new ObjectMapper();
    private final List<Product> productList = new ArrayList<>();
    private final Product product1 = new Product(1, "fanta", 16, "coca - cola", "1234");
    private final Product product2 = new Product(1, "7up", 14, "pepsico", "4321");
    private final ProductRequest getProductListRequset = new ProductRequest("products.getAll", "", "GET");
    private final ProductRequest addProductRequest = new ProductRequest("products.add", "addProductJson", "POST");
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ProductsService(client, mapper);
        productList.add(product1);
        productList.add(product2);
    }
    @Test
    public void getProducts_ShouldReturn_ListOfProducts() throws Exception {
        // Arrange
        ProductResponse response = new ProductResponse(getProductListRequset.getRequestId(), mapper.writeValueAsString(productList), 200);
        when(client.sendAndReceive(any())).thenReturn(response);
        // Act
        List<Product> result = subject.getProductsList();
        // Assert
        assertThat(result.size()).isEqualTo(productList.size());
        assertThat(result.get(0).getId()).isEqualTo(product1.getId());
    }
    @Test
    public void getProducts_ShouldThrow_Exception() {
        // Arrange
        ProductResponse response = new ProductResponse(getProductListRequset.getRequestId(), "error getting products", 500);
        when(client.sendAndReceive(any())).thenReturn(response);
        // Act
        Throwable t = catchThrowable(() -> subject.getProductsList());
        assertThat(t).isInstanceOf(Exception.class);
        assertThat(t.getMessage()).isEqualTo("error getting products");
    }
    @Test
    public void addProduct_ShouldReturn_1() throws Exception {
        // Arrange
        ProductResponse response = new ProductResponse(addProductRequest.getRequestId(), "1", 201);
        when(client.sendAndReceive(any())).thenReturn(response);
        // Act
        int result = subject.addProduct(product1);
        // Assert
        assertThat(result).isEqualTo(1);
    }
    @Test
    public void addProduct_ShouldReturn_0() throws Exception {
        // Arrange
        ProductResponse response = new ProductResponse(addProductRequest.getRequestId(), "1", 401);
        when(client.sendAndReceive(any())).thenReturn(response);
        // Act
        int result = subject.addProduct(product1);
        // Assert
        assertThat(result).isEqualTo(0);
    }
    @Test
    public void adProduct_Should_Throw_Exception() {
        // Arrange
        ProductResponse response = new ProductResponse(addProductRequest.getRequestId(), "error on create", 500);
        when(client.sendAndReceive(any())).thenReturn(response);
        // Act
        Throwable t = catchThrowable(() -> subject.addProduct(product2));
        // Assert
        assertThat(t).isInstanceOf(Exception.class);
        assertThat(t.getMessage()).isEqualTo("error on create");
    }
}