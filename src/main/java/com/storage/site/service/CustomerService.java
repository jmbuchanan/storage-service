package com.storage.site.service;

import com.storage.site.model.Customer;
import com.storage.site.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }

        customer.setAuthorities();
        return customer;

    }
}
