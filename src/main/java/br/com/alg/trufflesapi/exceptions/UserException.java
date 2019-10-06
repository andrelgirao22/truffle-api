package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class UserException extends RuntimeException implements Supplier<UserException>{

	private static final long serialVersionUID = 1L;

	public UserException() {
		super("User Not Found");
	}
	
	public UserException(String message) {
		super(message);
	}
	
	public UserException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public UserException get() {
		return this;
	}
	
}
