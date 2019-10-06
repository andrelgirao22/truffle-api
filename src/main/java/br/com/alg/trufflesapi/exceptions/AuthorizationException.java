package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class AuthorizationException extends RuntimeException implements Supplier<AuthorizationException> {

	private static final long serialVersionUID = 1L;


	public AuthorizationException(String message) {
		super(message);
	}
	
	public AuthorizationException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public AuthorizationException get() {
		return this;
	}
}
