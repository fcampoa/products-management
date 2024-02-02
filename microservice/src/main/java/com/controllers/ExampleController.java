package com.controllers;

import com.domain.Product;
import com.services.IProductsService;
import com.services.ProductsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/example")
public class ExampleController {
    private final IProductsService productsService;
    private static final Logger logger = LogManager.getLogger(ExampleController.class);
    @Autowired
    public ExampleController(IProductsService productsService) {
        this.productsService = productsService;
    }
    @GetMapping("hello")
    public String hello() {
        return "hello world from docker";
    }
    @GetMapping("getProducts")
    public ResponseEntity<?> getProducts() {
        try {
            return ResponseEntity.ok(productsService.getProducts());
        }
        catch(Exception ex) {
            logger.error("error: " + ex.getMessage());
            return ResponseEntity.ok("error: " + ex.getMessage());
        }
    }
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productsService.addProduct(product));
        }
        catch(Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
