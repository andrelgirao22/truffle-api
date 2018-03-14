package br.com.alg.trufflesapi.exceptions;

import java.util.function.Supplier;

public class GroupNotFoundException extends RuntimeException implements Supplier<GroupNotFoundException> {

	private static final long serialVersionUID = 1L;
	
	public GroupNotFoundException(String message) {
		super(message);
	}
	
	public GroupNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public GroupNotFoundException get() {
		return this;
	}

}
