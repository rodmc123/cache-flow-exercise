package com.cacheflow.invoice.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnumConverter implements Converter<String, InvoiceStatus> {
    @Override
    public InvoiceStatus convert(String value) {
        return InvoiceStatus.valueOf(value.toUpperCase());
    }
}

