package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class CategoryNotFoundException extends RuntimeException implements Supplier<CategoryNotFoundException>{

	private static final long serialVersionUID = 1L;
	
	public CategoryNotFoundException(String message) {
		super(message);
	}
	
	public CategoryNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	@Override
	public CategoryNotFoundException get() {
		return this;
	}

}
