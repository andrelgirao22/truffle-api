package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class ItemNotFoundException extends RuntimeException implements Supplier<ItemNotFoundException>{

	private static final long serialVersionUID = 1L;
	
	public ItemNotFoundException(String message) {
		super(message);
	}
	
	public ItemNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public ItemNotFoundException get() {
		return this;
	}

}
