package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class CityNotFoundException extends RuntimeException implements Supplier<CityNotFoundException> {

	private static final long serialVersionUID = 1L;


	public CityNotFoundException(String message) {
		super(message);
	}
	
	public CityNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public CityNotFoundException get() {
		return this;
	}
}
