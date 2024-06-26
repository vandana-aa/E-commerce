package com.ecommerce.SpringSecurity.repository;

import com.ecommerce.SpringSecurity.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Page<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);

        Optional<Product> findById(Integer id);

    }



