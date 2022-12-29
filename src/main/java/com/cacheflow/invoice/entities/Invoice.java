package com.cacheflow.invoice.entities;

import com.cacheflow.invoice.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Audited(targetAuditMode = NOT_AUDITED)
@Getter
@Setter
public class Invoice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;
    @NotAudited
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customer;
    @OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Item> items = new HashSet<>();
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "description")
    private String description;
}
