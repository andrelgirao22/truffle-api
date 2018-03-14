package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class PaymentInvalidException extends RuntimeException implements Supplier<PaymentInvalidException> {

	private static final long serialVersionUID = 1L;
	
	public PaymentInvalidException(String message) {
		super(message);
	}
	
	public PaymentInvalidException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public PaymentInvalidException get() {
		return this;
	}


}
