package org.code4cause.microservices.currencyconversionservice.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CurrencyConversionValue {

    private CurrencyMapping currencyMapping;

    private BigDecimal conversionValue;

    private int port;

}
