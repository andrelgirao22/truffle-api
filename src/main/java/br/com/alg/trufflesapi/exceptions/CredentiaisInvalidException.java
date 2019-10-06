package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class CredentiaisInvalidException extends RuntimeException implements Supplier<CredentiaisInvalidException>{

	private static final long serialVersionUID = 1L;

	public CredentiaisInvalidException() {
		super("Credentiais Not Found");
	}
	
	public CredentiaisInvalidException(String message) {
		super(message);
	}
	
	public CredentiaisInvalidException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public CredentiaisInvalidException get() {
		return this;
	}
	
}
