package com.revature.exceptions;

public class ClientNotDeletedException extends Exception {

	public ClientNotDeletedException() {
		super();
	}

	public ClientNotDeletedException(String message) {
		super(message);
	}

	public ClientNotDeletedException(Throwable cause) {
		super(cause);
	}

	public ClientNotDeletedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientNotDeletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
