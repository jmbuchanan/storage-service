package com.storage.site.service;

import com.storage.site.model.Customer;
import com.storage.site.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    public boolean customerExists(String email) {
        return customerRepository.findByEmail(email) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {
            String password = customer.getPassword();
            List<GrantedAuthority> authorities = new ArrayList<>();

            if (customer.isAdmin()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            User user = new User(email, password, authorities);
            return (UserDetails) user;

        } else {
            throw new UsernameNotFoundException("No user associated with this e-mail");
        }
    }
}
