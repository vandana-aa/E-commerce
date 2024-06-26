package com.ecommerce.SpringSecurity.service;


import com.ecommerce.SpringSecurity.exception.ProductNotFoundException;
import com.ecommerce.SpringSecurity.model.Product;
import com.ecommerce.SpringSecurity.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Page<Product> getProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }


    public Product getProductById(Integer id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }


    public Page<Product> searchProductsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findByCategoryContainingIgnoreCase(category, pageable);
    }

    public void disableProduct(Integer id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setAvailable(false); // Flag product as not available
        productRepo.save(product);
    }
}
