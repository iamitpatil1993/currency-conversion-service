package org.code4cause.microservices.currencyconversionservice;

import java.util.ConcurrentModificationException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.pattern.MessageConverter;

@Configuration
public class CommonConfig {

	/**
	 * As per spring doc {@link RestTemplate} is thread safe and can be shared by
	 * multiple threads safely. But that is not true.
	 * {@link RestTemplate#setMessageConverters(java.util.List)} methods are not
	 * thread safe and can be invoked by multiple threads concurrently. Which will
	 * result into {@link ConcurrentModificationException}
	 *
	 * As long as we assume that we will not update {@link MessageConverter}s at
	 * runtime OR we initialize all required {@link MessageConverter}s at creation
	 * time, we can reuse same {@link RestTemplate} in most of the cases
	 * 
	 * Search online regarding this.
	 * 
	 * @return {@link RestTemplate}
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
