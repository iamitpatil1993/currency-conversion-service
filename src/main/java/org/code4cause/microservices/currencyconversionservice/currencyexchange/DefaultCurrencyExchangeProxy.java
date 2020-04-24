package org.code4cause.microservices.currencyconversionservice.currencyexchange;

import org.code4cause.microservices.currencyconversionservice.configproperty.CurrencyExchangeServiceConfigProperty;
import org.code4cause.microservices.currencyconversionservice.dto.SuccessResponse;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyMapping;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CurrencyExchangeProxy}. Implements remote
 * proxy using {@link RestTemplate}
 *
 * @author amipatil
 */
@Slf4j
@Component
@AllArgsConstructor
public class DefaultCurrencyExchangeProxy implements CurrencyExchangeProxy, InitializingBean {

    private static final String GET_CURRENCY_MAPPING_URI = "/currency-exchange/from/{from}/to/{to}";
    private CurrencyExchangeServiceConfigProperty exchangeServiceConfigProperty;
    private RestTemplate restTemplate;

    @Override
    public CurrencyMapping getCurrencyMappingByFromAndToCurrency(final String from, final String to) {
    	try {
    		ParameterizedTypeReference<SuccessResponse<CurrencyMapping>> typeRef = new ParameterizedTypeReference<>() {};
        	ResponseEntity<SuccessResponse<CurrencyMapping>> responseEntity = restTemplate.exchange(buildRequestUri(GET_CURRENCY_MAPPING_URI), HttpMethod.GET, null, typeRef, from, to);
            return responseEntity.getBody().getData();
    	} catch (NotFound e) {
			return null;
		}
    }

    private String buildRequestUri(final String resourceUri) {
        return new StringBuilder(exchangeServiceConfigProperty.getBaseUrl()).append(resourceUri).toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(exchangeServiceConfigProperty, "NUll currencyExchangeServiceConfig injected");
        Assert.notNull(restTemplate, "NUll restTemplate injected");
    }
}
