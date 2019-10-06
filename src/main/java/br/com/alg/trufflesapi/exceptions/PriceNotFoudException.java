package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class PriceNotFoudException extends RuntimeException implements Supplier<PriceNotFoudException> {
	
	private static final long serialVersionUID = 1L;
	
	public  PriceNotFoudException(String message) {
		super(message);
	}
	
	public PriceNotFoudException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	@Override
	public PriceNotFoudException get() {
		return this;
	}

}
