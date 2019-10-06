package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class AccountNotFoundException extends RuntimeException implements Supplier<AccountNotFoundException> {

	private static final long serialVersionUID = 1L;


	public AccountNotFoundException(String message) {
		super(message);
	}
	
	public AccountNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public AccountNotFoundException get() {
		return this;
	}
}
