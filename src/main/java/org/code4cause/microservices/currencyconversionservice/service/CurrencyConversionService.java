package org.code4cause.microservices.currencyconversionservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code4cause.microservices.currencyconversionservice.currencyexchange.CurrencyExchangeProxy;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyConversionRequest;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyConversionValue;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyMapping;
import org.springframework.beans.factory.InitializingBean;
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

    public Optional<CurrencyConversionValue> convertCurrency(final CurrencyConversionRequest request) {
        final CurrencyMapping currencyMapping = currencyExchangeProxy
                .getCurrencyMappingByFromAndToCurrency(request.getFrom(), request.getTo());

        if (Objects.isNull(currencyMapping)) {
            return Optional.empty();
        }
        final CurrencyConversionValue currencyConversionValue = new CurrencyConversionValue();
        currencyConversionValue.setCurrencyMapping(currencyMapping);
        currencyConversionValue.setConversionValue(calculateConversionValue(request, currencyMapping));

        return Optional.of(currencyConversionValue);
    }

    private BigDecimal calculateConversionValue(final CurrencyConversionRequest request, final CurrencyMapping currencyMapping) {
        return request.getConversionQuantity().multiply(currencyMapping.getConversionFactor());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(currencyExchangeProxy, "currencyExchangeProxy injected null");
    }
}