package com.cacheflow.invoice.service.impl;

import com.cacheflow.invoice.entities.Customer;
import com.cacheflow.invoice.entities.Invoice;
import com.cacheflow.invoice.entities.Item;
import com.cacheflow.invoice.entities.Product;
import com.cacheflow.invoice.enums.InvoiceStatus;
import com.cacheflow.invoice.exception.BadRequestException;
import com.cacheflow.invoice.repository.CustomerRepository;
import com.cacheflow.invoice.repository.InvoiceRepository;
import com.cacheflow.invoice.repository.ProductRepository;
import com.cacheflow.invoice.request.InvoiceRequest;
import com.cacheflow.invoice.request.InvoiceStatusUpdateRequest;
import com.cacheflow.invoice.request.LineItemRequest;
import com.cacheflow.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> getInvoicesByStatus(InvoiceStatus status) {
        return invoiceRepository.findInvoicesByStatus(status);
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    @Override
    public void delete(Long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    @Override
    public Optional<Invoice> addLineItems(LineItemRequest request) {
      Optional<Invoice> invoice = invoiceRepository.findById(request.getInvoiceId());
      return invoice.map(i -> {
          List<Item> items = convertRequestToItems(request.getLineItems(), request.getInvoiceId());
          i.getItems().addAll(items);
          // Calculate invoice total
          i.setTotal(i.getTotal().add(BigDecimal.valueOf(request.getLineItems().stream()
                          .mapToDouble(it -> it.getTotal()
                                          .multiply(BigDecimal.valueOf(it.getQuantity()))
                                  .doubleValue())
                          .sum()))
                  .setScale(2, RoundingMode.HALF_UP));
          return invoiceRepository.save(i);
      });
    }

    private List<Item> convertRequestToItems(List<LineItemRequest.ItemRequest> itemsToAdd, Long invoiceId) {
       return itemsToAdd.stream().map(itemRequest -> {
            Item item = new Item();
            // check if product being added as part of the request already exist
            Product product = productRepository.findProductByModelAndSerialNumber(itemRequest.getModel(), itemRequest.getSerialNumber());
            if (product == null) {
                product = new Product();
                product.setTotal(itemRequest.getTotal());
                product.setDescription(itemRequest.getDescription());
                product.setManufacturer(itemRequest.getManufacturer());
                product.setCountryOfOrigin(itemRequest.getCountryOfOrigin());
                // model and serial number are unique fields to avid creating the same product multiple times
                product.setModel(itemRequest.getModel());
                product.setSerialNumber(itemRequest.getSerialNumber());
            }
            item.setQuantity(itemRequest.getQuantity());
            item.setProduct(product);
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Invoice> updateInvoice(InvoiceStatusUpdateRequest request) throws BadRequestException {
        // if the user session was available would check that the invoice being updated belongs to the logged in customer
        Optional<Invoice> invoice = getInvoiceById(request.getInvoiceId());
        return invoice.map(i -> {
            if (i.getStatus().equals(InvoiceStatus.DRAFT)) {
                i.setStatus(request.getInvoiceStatus());
                i = invoiceRepository.save(i);
            }
            return Optional.of(i);
        }).orElseThrow(()-> new BadRequestException("Invoice id:" + request.getInvoiceId() + " status can't be updated because its current status is not DRAFT"));
    }

    public Invoice createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = new Invoice();
        Customer customer = customerRepository.findCustomerByEmail(invoiceRequest.getCustomerEmail());
        if (customer == null) { // check if customer already exist otherwise create a new customer
            customer = new Customer();
            customer.setEmail(invoiceRequest.getCustomerEmail());
            customer.setFirstName(invoiceRequest.getCustomerName());
        }
        invoice.setDescription(invoiceRequest.getDescription());
        invoice.setDueDate(invoiceRequest.getDueDate());
        invoice.setStatus(invoiceRequest.getStatus());
        invoice.setTotal(invoiceRequest.getTotal());
        invoice.setCustomer(customer);
        return invoiceRepository.save(invoice);
    }

    /**
     * Send late invoice notification to a listening server
     */
    @Scheduled(cron = "0 7 * * *", zone = "America/New_York") // run once a day at 7 AM new york time
    public void sendInvoicePastDueDateNotification() {
        List<Invoice> invoicesPastDueDate = invoiceRepository.findInvoicesByDueDateBeforeAndStatusIsNot(LocalDate.now(), InvoiceStatus.SENT);
        invoicesPastDueDate.forEach(invoice -> {
            // probably use rest template to post a message to a URL that another service or the UI is listening to.
            // can post one message per late invoice or could just send a single message with the full list of late invoices
        });
    }
}
