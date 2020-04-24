package org.code4cause.microservices.currencyconversionservice.currencyexchange;

import org.code4cause.microservices.currencyconversionservice.model.CurrencyMapping;

/**
 * Act as a proxy to currency-service currency mapping resource Represents local
 * object for currency-service currency mapping API/Resource
 * 
 * @author amipatil
 *
 */
public interface CurrencyExchangeProxy {

	CurrencyMapping getCurrencyMappingByFromAndToCurrency(final String fromCurrency, final String toCurrency);
}