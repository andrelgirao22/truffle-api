package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class TaxDeliveryNotFoundException extends RuntimeException implements Supplier<TaxDeliveryNotFoundException> {

	private static final long serialVersionUID = 1L;

	public TaxDeliveryNotFoundException(String message) {
		super(message);
	}

	public TaxDeliveryNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	@Override
	public TaxDeliveryNotFoundException get() {
		return this;
	}

}
