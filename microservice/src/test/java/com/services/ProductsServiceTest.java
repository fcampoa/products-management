package com.services;

import com.domain.Product;
import com.repositories.IProductsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest {
    private IProductsService subject;
    @Mock
    private IProductsRepository productsRepository;
    private List<Product> productList = new ArrayList<>();
    private Product product1 = new Product(1, "fanta", 16, "coca-cola", "1234");
    private Product product2 = new Product(1, "7up", 14, "pepsico", "4321");
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        subject = new ProductsService(productsRepository);
        productList.clear();
    }
    @Test
    public void productService_getProducts_shouldReturns_ProductList() {
        // Arrange
        productList.add(product1);
        productList.add(product2);
        when(productsRepository.getProducts()).thenReturn(productList);
        // Act
        List<Product> result = subject.getProducts();
        // Assert
        assertThat(result.size()).isEqualTo(productList.size());
        assertThat(result.get(0)).isEqualTo(product1);
        assertThat(result.get(1)).isEqualTo(product2);
    }
    @Test
    public void productService_addProduct_shouldReturns_1() throws Exception {
        // Arrange
        when(productsRepository.addProduct(product1)).thenReturn(1);
        // Act
        int result = subject.addProduct(product1);
        // Assert
        assertThat(result).isEqualTo(1);
    }
    @Test
    public void productService_addProduct_shouldThrows_Exception_when_errorOnInsert() {
        // Arrange
        when(productsRepository.addProduct(product1)).thenThrow(MockitoException.class);
        // Act
        Throwable t = catchThrowable(() -> subject.addProduct(product1));
        // Assert
        assertThat(t).isInstanceOf(Exception.class);
    }
    @Test
    public void productService_addProduct_shouldThrows_Exceptiom_when_duplicatedProduct() {
        // Arrange
        when(productsRepository.checkDuplicated(product1.getBarCode())).thenReturn(1);
        // Act
        Throwable t = catchThrowable(() -> subject.addProduct(product1));
        // Assert
        assertThat(t).isInstanceOf(Exception.class);
        assertThat(t.getMessage()).isEqualTo("duplicated product with barCode: " + product1.getBarCode());
    }
}
