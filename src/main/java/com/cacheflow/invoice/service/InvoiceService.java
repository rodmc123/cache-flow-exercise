package com.cacheflow.invoice.service;

import com.cacheflow.invoice.entities.Invoice;
import com.cacheflow.invoice.entities.Item;
import com.cacheflow.invoice.enums.InvoiceStatus;
import com.cacheflow.invoice.exception.BadRequestException;
import com.cacheflow.invoice.request.InvoiceRequest;
import com.cacheflow.invoice.request.InvoiceStatusUpdateRequest;
import com.cacheflow.invoice.request.LineItemRequest;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

   /**
    * @param invoiceRequest
    * @return created invoice
    */
   Invoice createInvoice(InvoiceRequest invoiceRequest);

   /**
    * @return List of all invoices (not adding a limit or pagination for this example)
    */
   List<Invoice> getInvoices();

   /**
    * @param status
    * @return List of invoices in the given status
    */
   List<Invoice> getInvoicesByStatus(InvoiceStatus status);

   /**
    * @param invoiceId
    * @return invoice by id
    */
   Optional<Invoice> getInvoiceById(Long invoiceId);

   /**
    * Delete invoice by id
    * @param invoiceId
    */
   void delete(Long invoiceId);

   /**
    * @param lineItemRequest
    * @return invoice with added line items
    */
   Optional<Invoice> addLineItems(LineItemRequest lineItemRequest);

   /**
    * @param request
    * @return updated invoice entity
    * @throws BadRequestException
    */
   Optional<Invoice> updateInvoice(InvoiceStatusUpdateRequest request) throws BadRequestException;
}
