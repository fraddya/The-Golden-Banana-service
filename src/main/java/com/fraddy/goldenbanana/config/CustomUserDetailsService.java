package com.fraddy.goldenbanana.config;


import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User credential = repository.findByEmail(userName);
        if (credential == null) {
            throw new UsernameNotFoundException("user not found with user name :" + userName);
        }
        return new CustomUserDetails(credential);
        //return credential(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with email :" + email));
    }
}
