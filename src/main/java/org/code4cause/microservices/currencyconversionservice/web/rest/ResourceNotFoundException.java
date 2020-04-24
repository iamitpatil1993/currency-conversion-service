package org.code4cause.microservices.currencyconversionservice.web.rest;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3625705383257713071L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}