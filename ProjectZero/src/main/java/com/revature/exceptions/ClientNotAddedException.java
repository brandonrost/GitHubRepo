package com.revature.exceptions;

public class ClientNotAddedException extends Exception {

	public ClientNotAddedException() {
		super();
	}

	public ClientNotAddedException(String message) {
		super(message);
	}

	public ClientNotAddedException(Throwable cause) {
		super(cause);
	}

	public ClientNotAddedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientNotAddedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
