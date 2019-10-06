package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class ImageNotFoundException extends RuntimeException implements Supplier<ImageNotFoundException>{

	private static final long serialVersionUID = 1L;
	
	public ImageNotFoundException(String message) {
		super(message);
	}
	
	public ImageNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public ImageNotFoundException get() {
		return this;
	}

}
