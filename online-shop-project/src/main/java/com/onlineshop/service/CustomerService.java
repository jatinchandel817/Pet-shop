package com.onlineshop.service;

import com.onlineshop.entity.Customer;
import com.onlineshop.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer saveOrUpdate(Customer customer) {
        return repository.findByEmail(customer.getEmail()).map(existing -> {
            existing.setName(customer.getName());
            existing.setPhone(customer.getPhone());
            existing.setAddress(customer.getAddress());
            existing.setCity(customer.getCity());
            existing.setState(customer.getState());
            existing.setPincode(customer.getPincode());
            return repository.save(existing);
        }).orElseGet(() -> repository.save(customer));
    }
}
