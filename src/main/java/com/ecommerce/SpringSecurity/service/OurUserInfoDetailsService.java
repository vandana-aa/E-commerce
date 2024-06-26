package com.ecommerce.SpringSecurity.service;

import com.ecommerce.SpringSecurity.model.OurUser;
import com.ecommerce.SpringSecurity.repository.OurUserRepo;
import com.ecommerce.SpringSecurity.model.OurUserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OurUserInfoDetailsService implements UserDetailsService {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OurUser> user = ourUserRepo.findByEmail(username);
        return user.map(OurUserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }
}
