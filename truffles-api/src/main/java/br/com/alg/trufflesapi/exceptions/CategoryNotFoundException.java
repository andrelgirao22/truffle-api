package br.com.alg.trufflesapi.exceptions;

public class CategoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CategoryNotFoundException(String message) {
		super(message);
	}
	
	public CategoryNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
