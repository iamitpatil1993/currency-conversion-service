package org.code4cause.microservices.currencyconversionservice.config.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClients({ @RibbonClient(name = "currency-exchange-service", configuration = DefaultRibbonClientConfig.class) })
public class RibbonClientConfiguration {

}
