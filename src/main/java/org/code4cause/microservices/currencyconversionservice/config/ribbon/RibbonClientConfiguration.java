package org.code4cause.microservices.currencyconversionservice.config.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;

/**
 * <p>
 * Spring cloud creates separate ApplicationContext per ribbon client to house
 * beans required for ribbon load balancing.
 * </p>
 * <p>
 * Because, each ribbon client may needs different configuration and hence need
 * to define {@link IRule},, {@link IClientConfig} and {@link IPing} beans, we
 * can not define multiple beans of same type (we can but how we will tell
 * particular ribbon client to use bean with particular name or qualifier.
 * </p>
 * 
 * <p>
 * Hence spring defines separate {@link ApplicationContext} per
 * {@link RibbonClient} so that we can define configuration beans for that
 * particular ribbon client in it's own {@link ApplicationContext} But, in our
 * case here, all these beans are part of same root config hence they are shared
 * by all ribbon clients.
 * </p>
 * 
 * @author amipatil
 *
 */
@Configuration
// This RibbonClient declaration is optional unless we do not want to customize the configurations. Refer 
@RibbonClients({ @RibbonClient(name = "currency-exchange-service", configuration = DefaultRibbonClientConfig.class) })
public class RibbonClientConfiguration {

}
