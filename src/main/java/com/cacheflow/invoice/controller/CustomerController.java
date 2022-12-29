package com.cacheflow.invoice.controller;

import com.cacheflow.invoice.entities.Customer;
import com.cacheflow.invoice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long customerId) {
        // passing ID directly for this exercise. Would usually take the customer id from the user session and also use UUID for entity ID's instead of Long
        return ResponseEntity.ok(customerService.getCustomerById(customerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer id: " + customerId + " not found")));
    }
}
