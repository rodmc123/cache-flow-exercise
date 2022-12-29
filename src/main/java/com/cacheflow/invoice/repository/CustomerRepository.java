package com.cacheflow.invoice.repository;

import com.cacheflow.invoice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, RevisionRepository<Customer, Long, Long> {
     Customer findCustomerByEmail(String email);
}
