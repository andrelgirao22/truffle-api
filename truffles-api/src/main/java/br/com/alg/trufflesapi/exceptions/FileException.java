package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class FileException extends RuntimeException implements Supplier<FileException>{

	private static final long serialVersionUID = 1L;
	
	public FileException(String message) {
		super(message);
	}
	
	public FileException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	@Override
	public FileException get() {
		return this;
	}

}
