package com.cacheflow.invoice.repository;

import com.cacheflow.invoice.entities.Invoice;
import com.cacheflow.invoice.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, RevisionRepository<Invoice, Long, Long> {
       List<Invoice> findInvoicesByStatus(InvoiceStatus status);
       //Using inferred queries
       List<Invoice> findInvoicesByDueDateBeforeAndStatusIsNot(LocalDate date, InvoiceStatus Status);
}
