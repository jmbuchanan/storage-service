package com.storage.site.repository;

import com.storage.site.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public Customer findByEmail(String email);

}
