package org.code4cause.microservices.currencyconversionservice.config.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;

/**
 * We can override any Ribbon-related bean that Spring Cloud Netflix gives us by
 * creating our own bean with the same name.
 * <p>
 * Here, we override the IPing and IRule used by the default load balancer.
 * </p>
 * <p>
 * The default IPing is a NoOpPing (which doesn’t actually ping server
 * instances, instead always reporting that they’re stable) , and the default
 * IRule is a ZoneAvoidanceRule (which avoids the Amazon EC2 zone that has the
 * most malfunctioning servers, and might thus be a bit difficult to try out in
 * our local environment).
 * </p>
 * 
 * <p>
 * There can be separate config class per ribbon client and can have separate
 * {@link IRule}, {@link IClientConfig} and {@link IPing} beans per ribbon
 * client. So that we can customize each {@link RibbonClient} separately as we
 * wish.
 * </p>
 * 
 * <p>
 * We should not annotate this with @Configuration because, then this
 * bean/config will become config in root ApplicationContext. and will get
 * shared by all RibbonClients which we do not want. We want this bean to be
 * part of RibbonClient's own ApplicationContext.
 * </p>
 * <p>
 * refer:
 * </p>
 * <ul>
 * <li>https://stackoverflow.com/questions/39587317/difference-between-ribbonclient-and-loadbalanced</li>
 * <li>https://spring.io/guides/gs/spring-cloud-loadbalancer/</li>
 * </ul>
 * 
 * @author amipatil
 *
 */

public class DefaultRibbonClientConfig {

	@Autowired
	public IClientConfig clientConfig;

	/**
	 * Our IPing is a PingUrl, which will ping a URL to check the status of each
	 * server.
	 * 
	 * @param clientConfig
	 * @return
	 */
	@Bean
	public IPing iPing(final IClientConfig clientConfig) {
		return new PingUrl();
	}

	/**
	 * The IRule we set up, the AvailabilityFilteringRule, will use Ribbon’s
	 * built-in circuit breaker functionality to filter out any servers in an
	 * “open-circuit” state: if a ping fails to connect to a given server, or if it
	 * gets a read failure for the server, Ribbon will consider that server “dead”
	 * until it begins to respond normally.
	 * 
	 * @param clientConfig
	 * @return
	 */
	public IRule iRule(final IClientConfig clientConfig) {
		return new AvailabilityFilteringRule();
	}

}
