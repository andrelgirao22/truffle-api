package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class DeliveryNotFoundException extends RuntimeException implements Supplier<DeliveryNotFoundException> {

	private static final long serialVersionUID = 1L;
	
	public DeliveryNotFoundException(String message) {
		super(message);
	}
	
	public DeliveryNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	@Override
	public DeliveryNotFoundException get() {
		return this;
	}

}
