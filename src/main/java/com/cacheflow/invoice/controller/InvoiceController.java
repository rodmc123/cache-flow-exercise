package com.cacheflow.invoice.controller;

import com.cacheflow.invoice.entities.Invoice;
import com.cacheflow.invoice.enums.InvoiceStatus;
import com.cacheflow.invoice.exception.BadRequestException;
import com.cacheflow.invoice.request.InvoiceRequest;
import com.cacheflow.invoice.request.InvoiceStatusUpdateRequest;
import com.cacheflow.invoice.request.LineItemRequest;
import com.cacheflow.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestBody @Validated InvoiceRequest invoiceRequest) {
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Invoice>> getInvoices() {
        return ResponseEntity.ok(invoiceService.getInvoices());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") Long invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(invoiceId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice id: " + invoiceId + " not found")));
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable("status") InvoiceStatus status) {
        return ResponseEntity.ok(invoiceService.getInvoicesByStatus(status));
    }

    @PutMapping("/update")
    public ResponseEntity<Invoice> updateInvoiceById(@RequestBody @Validated InvoiceStatusUpdateRequest request) throws BadRequestException {
        return ResponseEntity.ok(invoiceService.updateInvoice(request).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice id: " + request.getInvoiceId() + " not found")));
    }

    @PutMapping("/add-line-item")
    public ResponseEntity<Invoice> addLineItem(@RequestBody @Validated LineItemRequest request) throws BadRequestException {
        return ResponseEntity.ok(invoiceService.addLineItems(request).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice id: " + request.getInvoiceId() + " not found")));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long invoiceId) {
        invoiceService.delete(invoiceId);
        return ResponseEntity.ok().build();
    }
}
