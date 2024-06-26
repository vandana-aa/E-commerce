package com.ecommerce.SpringSecurity.service;

import com.ecommerce.SpringSecurity.model.Product;
import com.ecommerce.SpringSecurity.repository.ProductRepo;
import com.ecommerce.SpringSecurity.model.CartItem;
import com.ecommerce.SpringSecurity.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> processOrder(Map<String, List<CartItem>> cart) {
        List<Product> products = new ArrayList<>();
        for (Map.Entry<String, List<CartItem>> entry : cart.entrySet()) {
            for (CartItem item : entry.getValue()) {
                Product product = productRepo.findById(item.getId())
                        .orElseThrow(() -> new ProductNotFoundException(item.getId()));
                product.setAvailable(false); // Flag product as not available
                productRepo.save(product);
                products.add(product);
            }
        }
        return products;
    }
}

