package com.controllers;

import com.clients.RabbitMQClient;
import com.domain.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.requests.ProductRequest;
import com.responses.ProductResponse;
import com.services.IProductsService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final IProductsService productsService;
    private final RegularExpression regex = new RegularExpression("^(([a-z0-9])*\\s*|\\-([a-z0-9]))*(?!([\\/@\\_\\*\\<\\>]))$");
    private static final Logger logger = LogManager.getLogger(ProductsController.class);
    public ProductsController(IProductsService productsService) {
        this.productsService = productsService;
    }
    @GetMapping("get")
    public String productsList(Model model) {

        List<Product> productsList;
        try {
            productsList = productsService.getProductsList();
        } catch ( Exception ex) {
            logger.error(ex.getMessage());
            productsList = new ArrayList<>();
        }
        model.addAttribute("productsList", productsList);
        return "productsList";
    }
    @GetMapping("add")
    public String addProduct(Model model) {
        return "product";
    }
    @PostMapping()
    public RedirectView registerProduct(RedirectAttributes attributes, Product product) {
        try {
            if (!regex.matches(product.getName()) || !regex.matches(product.getBrand()) || !regex.matches(product.getBarCode())) {
                attributes.addFlashAttribute("errorMessage", "invalid value for some input");
                return new RedirectView("products/add");
            }
            int result = productsService.addProduct(product);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage());
        }
        attributes.addFlashAttribute("message", "product with barCode " + product.getBarCode() + " created");
        return new RedirectView("products/get");
    }
}