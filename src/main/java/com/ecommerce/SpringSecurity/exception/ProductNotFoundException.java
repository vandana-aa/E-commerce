package com.ecommerce.SpringSecurity.exception;

public class ProductNotFoundException extends RuntimeException {


        public ProductNotFoundException(Integer id) {
            super("Product not found with id: " + id);
        }
    }


