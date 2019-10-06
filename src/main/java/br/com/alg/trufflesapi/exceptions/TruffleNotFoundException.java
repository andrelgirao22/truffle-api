package br.com.alg.trufflesapi.exceptions;

public class TruffleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TruffleNotFoundException(String message) {
		super(message);
	}
	
	public TruffleNotFoundException(String message, Throwable trouble) {
		super(message, trouble);
	}
}
