package org.code4cause.microservices.currencyconversionservice.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyMapping {
	
	private Long id;

	private String fromCurrency;

	private String toCurrency;

	private BigDecimal conversionFactor;

	private int port;

}