package br.com.alg.trufflesapi.exceptions;

public class TokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenNotFoundException() {
		super("Token Not Found");
	}
	
	public TokenNotFoundException(String message) {
		super(message);
	}
	
	public TokenNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
