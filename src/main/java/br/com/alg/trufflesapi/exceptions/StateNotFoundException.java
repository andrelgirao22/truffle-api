package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class StateNotFoundException extends RuntimeException implements Supplier<StateNotFoundException> {

	private static final long serialVersionUID = 1L;


	public StateNotFoundException(String message) {
		super(message);
	}
	
	public StateNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public StateNotFoundException get() {
		return this;
	}
}
