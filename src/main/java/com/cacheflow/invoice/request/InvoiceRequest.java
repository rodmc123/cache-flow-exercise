package com.cacheflow.invoice.request;

import com.cacheflow.invoice.enums.InvoiceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceRequest {
    @NotEmpty
    private String customerEmail;
    @NotEmpty
    private String customerName;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDate dueDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    @NotNull
    private BigDecimal total;
}
