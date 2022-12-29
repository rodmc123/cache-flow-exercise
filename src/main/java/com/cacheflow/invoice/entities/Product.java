package com.cacheflow.invoice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"model", "serial_number"})})
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "country_of_origin")
    private String countryOfOrigin;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;
    @Column(name = "total", nullable = false)
    private BigDecimal total;
}
