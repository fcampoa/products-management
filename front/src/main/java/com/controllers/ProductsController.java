package com.controllers;

import com.clients.RabbitMQClient;
import com.domain.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.requests.ProductRequest;
import com.responses.ProductResponse;
import com.services.IProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    IProductsService productsService;
    public ProductsController( ObjectMapper mapper, IProductsService productsService) {
        this.productsService = productsService;
    }
    @GetMapping("get")
    public String productsList(Model model) {

        List<Product> productsList;
        try {
            productsList = productsService.getProductsList();
        } catch ( Exception ex) {
            productsList = new ArrayList<>();
        }
        model.addAttribute("productsList", productsList);
        return "productsList";
    }
    @GetMapping("add")
    public String addProduct(Model model) {
//        Product product = new Product();
//        model.addAttribute("product", product);
        return "product";
    }
    @PostMapping("register")
    public String registerProduct(Product product) {
        try {
            int result = productsService.addProduct(product);
        }
        catch(Exception ex) {
            return "error";
        }
        return "productsList";
    }
}