package com.revature.exceptions;

public class AccountTypeMismatchException extends Exception {

	public AccountTypeMismatchException() {
		super();
	}

	public AccountTypeMismatchException(String message) {
		super(message); 
	}

	public AccountTypeMismatchException(Throwable cause) {
		super(cause);
	}

	public AccountTypeMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountTypeMismatchException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
