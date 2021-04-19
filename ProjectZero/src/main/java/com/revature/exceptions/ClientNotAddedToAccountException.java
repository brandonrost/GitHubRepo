package com.revature.exceptions;

public class ClientNotAddedToAccountException extends Exception {

	public ClientNotAddedToAccountException() {
		super();
	}

	public ClientNotAddedToAccountException(String message) {
		super(message);
	}

	public ClientNotAddedToAccountException(Throwable cause) {
		super(cause);
	}

	public ClientNotAddedToAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientNotAddedToAccountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
