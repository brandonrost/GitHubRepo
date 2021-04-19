package com.revature.exceptions;

public class NoAccountsFoundException extends Exception {

	public NoAccountsFoundException() {
		super();
	}

	public NoAccountsFoundException(String message) {
		super(message);
	}

	public NoAccountsFoundException(Throwable cause) {
		super(cause);
	}

	public NoAccountsFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAccountsFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
