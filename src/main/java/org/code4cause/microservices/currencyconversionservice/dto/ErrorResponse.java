package org.code4cause.microservices.currencyconversionservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorResponse extends BaseResponse {
	
	public ErrorResponse() {
		setSucceess(false);
	}
	
	public ErrorResponse(final String errorMessage) {
		super(false, errorMessage);
	}
}