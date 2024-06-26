package com.ecommerce.SpringSecurity.service;

import com.ecommerce.SpringSecurity.model.OurUser;
import com.ecommerce.SpringSecurity.model.OurUserInfoDetails;
import com.ecommerce.SpringSecurity.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private OurUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OurUser> ourUser = userRepo.findByEmail(username);
        if (ourUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new OurUserInfoDetails(ourUser.orElse(null));
    }
}
