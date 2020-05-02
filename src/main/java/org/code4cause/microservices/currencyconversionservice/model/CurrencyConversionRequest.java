package org.code4cause.microservices.currencyconversionservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyConversionRequest {

    private String from;

    private String to;

    private BigDecimal conversionQuantity;
}
