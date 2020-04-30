package org.code4cause.microservices.currencyconversionservice.service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code4cause.microservices.currencyconversionservice.currencyexchange.CurrencyExchangeProxy;
import org.code4cause.microservices.currencyconversionservice.currencyexchange.FeignCurrencyExchangeProxy;
import org.code4cause.microservices.currencyconversionservice.dto.SuccessResponse;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyConversionRequest;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyConversionValue;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyMapping;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
@Service
@Slf4j
@AllArgsConstructor
public class CurrencyConversionService implements InitializingBean {

    private CurrencyExchangeProxy currencyExchangeProxy;

    private FeignCurrencyExchangeProxy feignCurrencyExchangeProxy;

    public Optional<CurrencyConversionValue> convertCurrency(final CurrencyConversionRequest request) {
        final CurrencyMapping currencyMapping = currencyExchangeProxy
                .getCurrencyMappingByFromAndToCurrency(request.getFrom(), request.getTo());

        if (Objects.isNull(currencyMapping)) {
            return Optional.empty();
        }
        final CurrencyConversionValue currencyConversionValue = getConversionValue(request, currencyMapping);

        return Optional.of(currencyConversionValue);
    }

    private CurrencyConversionValue getConversionValue(final CurrencyConversionRequest request, final CurrencyMapping currencyMapping) {
        final CurrencyConversionValue currencyConversionValue = new CurrencyConversionValue();
        currencyConversionValue.setCurrencyMapping(currencyMapping);
        currencyConversionValue.setConversionValue(calculateConversionValue(request, currencyMapping));
        return currencyConversionValue;
    }

    private BigDecimal calculateConversionValue(final CurrencyConversionRequest request, final CurrencyMapping currencyMapping) {
        return request.getConversionQuantity().multiply(currencyMapping.getConversionFactor());
    }

    /**
     * Uses Spring cloud feign based Currency exchange proxy.
     */
    public Optional<CurrencyConversionValue> convertCurrencyUsingFeign(final CurrencyConversionRequest request) {
        try {
            final ResponseEntity<SuccessResponse<CurrencyMapping>> responseEntity = feignCurrencyExchangeProxy
                    .getCurrencyMapping(request.getFrom(), request.getTo());
            final CurrencyConversionValue conversionValue = getConversionValue(request, responseEntity.getBody().getData());
            return Optional.of(conversionValue);
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(currencyExchangeProxy, "currencyExchangeProxy injected null");
    }
}