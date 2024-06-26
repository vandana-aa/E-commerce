package com.ecommerce.SpringSecurity.controller;

import com.ecommerce.SpringSecurity.model.OurUser;
import com.ecommerce.SpringSecurity.model.Product;
import com.ecommerce.SpringSecurity.repository.OurUserRepo;
import com.ecommerce.SpringSecurity.repository.ProductRepo;
import com.ecommerce.SpringSecurity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String goHome(){
        return "This is publicly accessible withing needing authentication ";
    }
    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody OurUser ourUser){
        ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
        OurUser result =ourUserRepo.save(ourUser);
        if (result.getId() > 0){
            return ResponseEntity.ok("User Was Saved");
        }
        return ResponseEntity.status(404).body("Error, USer Not Saved");
    }
    @GetMapping("/product/all")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productRepo.findAll());
    }
    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(ourUserRepo.findAll());
    }

    @GetMapping("/users/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getMyDetails(){
        return ResponseEntity.ok(ourUserRepo.findByEmail(getLoggedInUserDetails().getUsername()));
    }

    public UserDetails getLoggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    @Autowired
    private ProductService productService;

//    @GetMapping("/products/search")
//    public Page<Product> searchProducts(@RequestParam String category,
//                                        @RequestParam int page,
//                                        @RequestParam int size) {
//        return productService.searchProductsByCategory(category, page, size);
//    }
}

