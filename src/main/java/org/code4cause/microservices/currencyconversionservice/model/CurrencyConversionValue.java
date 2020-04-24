package org.code4cause.microservices.currencyconversionservice.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyConversionValue {

    private CurrencyMapping currencyMapping;

    private BigDecimal conversionValue;

    private int port;

}
