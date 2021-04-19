package com.revature.exceptions;

public class AccountNameNullException extends Exception {

	public AccountNameNullException() {
		super();
	}

	public AccountNameNullException(String message) {
		super(message);
	}

	public AccountNameNullException(Throwable cause) {
		super(cause);
	}

	public AccountNameNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNameNullException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
