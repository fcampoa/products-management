package com.services;

import com.domain.Product;
import com.repositories.IProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductsService implements IProductsService{
    private final IProductsRepository productsRepository;

    @Autowired
    public ProductsService(IProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }
    public List<Product> getProducts() {
        return productsRepository.getProducts();
    }
    public int addProduct(Product product) throws Exception {
        if (productsRepository.checkDuplicated(product.getBarCode()) > 0) {
            throw new Exception("duplicated product with barCode: " + product.getBarCode());
        }
        return productsRepository.addProduct(product);
    }
}
