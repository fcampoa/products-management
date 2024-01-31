package com.services;

import com.domain.Product;
import java.util.List;

public interface IProductsService {
    List<Product> getProductsList() throws Exception;
    int addProduct(Product product) throws Exception;
}

