package org.code4cause.microservices.currencyconversionservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class SuccessResponse<T> extends BaseResponse {

	private T data;

	public SuccessResponse(String message, T data) {
		super(true, message);
		this.data = data;
	}

	public SuccessResponse(String message) {
		super(true, message);
	}
}