package com.cacheflow.invoice.service;

import com.cacheflow.invoice.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    /**
     * @return all customers
     */
    List<Customer> getCustomers();

    /**
     * @param customerId
     * @return customer by id
     */
    Optional<Customer> getCustomerById(Long customerId);

}
