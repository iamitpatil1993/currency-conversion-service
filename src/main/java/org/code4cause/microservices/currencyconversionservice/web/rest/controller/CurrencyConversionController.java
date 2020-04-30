package org.code4cause.microservices.currencyconversionservice.web.rest.controller;

import org.code4cause.microservices.currencyconversionservice.dto.SuccessResponse;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyConversionRequest;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyConversionValue;
import org.code4cause.microservices.currencyconversionservice.service.CurrencyConversionService;
import org.code4cause.microservices.currencyconversionservice.web.rest.ResourceNotFoundException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class CurrencyConversionController implements InitializingBean {

    private CurrencyConversionService conversionService;

    @Value("${server.port}")
    private int port;

    public CurrencyConversionController(final CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public SuccessResponse<CurrencyConversionValue> handleConvertCurrency(final @PathVariable String from,
                                                                          final @PathVariable String to,
                                                                          final @PathVariable BigDecimal quantity) {
        final CurrencyConversionRequest request = new CurrencyConversionRequest(from, to, quantity);
        final Optional<CurrencyConversionValue> conversionValueOptional = conversionService.convertCurrency(request);
        return handleCurrencyConversionResponse(conversionValueOptional);
    }

    /**
     * Uses Spring cloud feign to communicate with currency-exchange-service
     */
    @GetMapping(path = "/currency-conversion/feign/from/{from}/to/{to}/quantity/{quantity}")
    public SuccessResponse<CurrencyConversionValue> handleConvertCurrencyWithFiegn(final @PathVariable String from,
                                                                                   final @PathVariable String to,
                                                                                   final @PathVariable BigDecimal quantity) {
        final CurrencyConversionRequest request = new CurrencyConversionRequest(from, to, quantity);
        final Optional<CurrencyConversionValue> conversionValueOptional = conversionService.convertCurrencyUsingFeign(request);
        return handleCurrencyConversionResponse(conversionValueOptional);
    }

    private SuccessResponse<CurrencyConversionValue> handleCurrencyConversionResponse(Optional<CurrencyConversionValue> conversionValueOptional) {
        if (conversionValueOptional.isEmpty()) {
            throw new ResourceNotFoundException("Conversion mapping not found");
        }
        final CurrencyConversionValue currencyConversionValue = conversionValueOptional.get();
        currencyConversionValue.setPort(port);
        return new SuccessResponse<>(null, currencyConversionValue);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(conversionService, "conversionService injected null");
    }
}