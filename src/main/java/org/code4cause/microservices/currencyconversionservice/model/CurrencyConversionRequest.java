package org.code4cause.microservices.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CurrencyConversionRequest {

    private String from;

    private String to;

    private BigDecimal conversionQuantity;
}
