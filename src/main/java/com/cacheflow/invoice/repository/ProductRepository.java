package com.cacheflow.invoice.repository;

import com.cacheflow.invoice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByModelAndSerialNumber(String model, String serialNumber);
}
