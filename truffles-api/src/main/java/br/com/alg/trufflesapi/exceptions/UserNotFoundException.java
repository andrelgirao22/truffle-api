package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class UserNotFoundException extends RuntimeException implements Supplier<UserNotFoundException>{

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("User Not Found");
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public UserNotFoundException get() {
		return this;
	}
	
}
