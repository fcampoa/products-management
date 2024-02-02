package com.services;

import com.domain.Product;
import com.repositories.IProductsRepository;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
@Service
public class ProductsService implements IProductsService{
    private final IProductsRepository productsRepository;
    private final RegularExpression regex = new RegularExpression("^(([a-z0-9])*\\s*|\\-([a-z0-9]))*(?!([\\/@\\_\\*\\<\\>]))$");
    private static final Logger logger = LogManager.getLogger(ProductsService.class);

    @Autowired
    public ProductsService(IProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }
    public List<Product> getProducts() {
        return productsRepository.getProducts();
    }
    public int addProduct(Product product) throws Exception {
        if (productsRepository.checkDuplicated(product.getBarCode()) > 0) {
            logger.error("duplicated product with barCode: " + product.getBarCode());
            throw new Exception("duplicated product with barCode: " + product.getBarCode());
        }
        if (!regex.matches(product.getName())) {
            logger.error("invalid value in field" + product.getName());
            throw new Exception("invalid value " + product.getName());
        }
        if (!regex.matches(product.getBrand())) {
            logger.error("invalid value in field " + product.getBrand());
            throw new Exception("invalid value " + product.getBrand());
        }
        if (!regex.matches(product.getBarCode())) {
            logger.error("invalid value in field" + product.getBarCode());
            throw new Exception("invalid value " + product.getBarCode());
        }
        logger.info("product with barCode " + product.getBarCode() + " created");
        return productsRepository.addProduct(product);
    }
}
