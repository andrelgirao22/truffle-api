package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class OrderNotFoundException extends RuntimeException implements Supplier<OrderNotFoundException> {

private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException(String message) {
		super(message);
	}
	
	public OrderNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	@Override
	public OrderNotFoundException get() {
		return this;
	}

}
