package com.cacheflow.invoice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LineItemRequest {
    private Long invoiceId;
    private List<ItemRequest> lineItems;


@Getter
@Setter
@NoArgsConstructor
public static class ItemRequest {
    private String description;
    private String countryOfOrigin;
    private String manufacturer;
    private String model;
    private String serialNumber;
    private BigDecimal total;
    private Integer quantity;
}
}