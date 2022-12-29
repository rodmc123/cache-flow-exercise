package com.cacheflow.invoice.request;

import com.cacheflow.invoice.enums.InvoiceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceStatusUpdateRequest {
    @NotNull
    private Long invoiceId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
}
