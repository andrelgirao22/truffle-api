package br.com.alg.trufflesapi.exceptions;

public class UserInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserInvalidException() {
		super();
	}
	
	public UserInvalidException(String message) {
		super(message);
	}
	
	public UserInvalidException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
