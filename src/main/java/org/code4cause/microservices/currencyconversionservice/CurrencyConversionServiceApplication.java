package org.code4cause.microservices.currencyconversionservice;

import org.code4cause.microservices.currencyconversionservice.currencyexchange.FeignCurrencyExchangeProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//We need to enable FeignClient before using it. It actually scans classes annotated with @FiengClient
// we can configure component scanning as usual.
@EnableFeignClients(clients = {FeignCurrencyExchangeProxy.class}) // we can list specific classes as well if we want to reduce scan time and have limited classes
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}

}
