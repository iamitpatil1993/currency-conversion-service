package org.code4cause.microservices.currencyconversionservice.web.rest;

import org.code4cause.microservices.currencyconversionservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(final ResourceNotFoundException exception) {
        final String errorMssage = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMssage);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> genericExceptionHandler(final Exception exception) {
		final String errorMssage = "Something went wrong while processing request. Try again later";
		ErrorResponse errorResponse = new ErrorResponse(errorMssage);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}