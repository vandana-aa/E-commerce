package com.ecommerce.SpringSecurity.repository;

import com.ecommerce.SpringSecurity.model.OurUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


     public interface OurUserRepo extends JpaRepository<OurUser, String> {

    @Query(value = "select * from ourusers where email=?1", nativeQuery = true)
    Optional<OurUser> findByEmail(String email);


}

