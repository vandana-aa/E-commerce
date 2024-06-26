package com.ecommerce.SpringSecurity.controller;

import com.ecommerce.SpringSecurity.model.Product;
import com.ecommerce.SpringSecurity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<Product> getAllProducts(@RequestParam int page, @RequestParam int size) {
        return productService.getProducts(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")   // works on web
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search/{category}")
    public Page<Product> searchProductsByCategory(@RequestParam String category, @RequestParam int page, @RequestParam int size) {
        return productService.searchProductsByCategory(category, page, size);

    }
}
