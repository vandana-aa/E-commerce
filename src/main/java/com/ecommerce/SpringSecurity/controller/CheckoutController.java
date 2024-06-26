package com.ecommerce.SpringSecurity.controller;

import com.ecommerce.SpringSecurity.model.CartItem;
import com.ecommerce.SpringSecurity.model.Product;
import com.ecommerce.SpringSecurity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<List<Product>> checkout(@RequestBody Map<String, List<CartItem>> cart) {
        List<Product> products = orderService.processOrder(cart);
        return ResponseEntity.ok(products);
    }
}

