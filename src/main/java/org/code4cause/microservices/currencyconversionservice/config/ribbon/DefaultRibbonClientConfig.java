package org.code4cause.microservices.currencyconversionservice.config.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
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
