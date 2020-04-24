package org.code4cause.microservices.currencyconversionservice.configproperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "service.config.currency-exchange")
@Data
public class CurrencyExchangeServiceConfigProperty {

	private String baseUrl;
}
