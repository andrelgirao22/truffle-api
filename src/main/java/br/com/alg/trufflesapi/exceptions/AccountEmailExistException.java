package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class AccountEmailExistException extends RuntimeException implements Supplier<AccountEmailExistException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountEmailExistException(String message) {
		super(message);
	}
	
	public AccountEmailExistException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public AccountEmailExistException get() {
		return this;
	}
		
}
