package org.code4cause.microservices.currencyconversionservice.currencyexchange;

import org.code4cause.microservices.currencyconversionservice.dto.SuccessResponse;
import org.code4cause.microservices.currencyconversionservice.model.CurrencyMapping;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Proxy using feign to communicate wit currency-exchange-service
 */
// spring.application.name here is not important for now, unless we use eureka naming server and ribbon.
// For now url parameter is only required and usefule
// Feign client interfaces are declared as  beans in application context with fully qualified name as a bean id
@FeignClient(name = "currency-exchange-service")

// this name currently getting used to configure static instance list in application.properties. Later this application
// name will be used to lookup instance list from Eureka naming server(by spring.application.name) for dynamic load balancing.
@RibbonClient(name = "currency-exchange-service")
public interface FeignCurrencyExchangeProxy {

    /**
     * This method declaration is exactly same as in currency-exchange-service controller.
     */
    @GetMapping(path = {"/currency-exchange/from/{from}/to/{to}"})
    ResponseEntity<SuccessResponse<CurrencyMapping>> getCurrencyMapping(@PathVariable String from,
                                                                               @PathVariable String to);
}
