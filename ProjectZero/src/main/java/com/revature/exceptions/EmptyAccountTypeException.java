package com.revature.exceptions;

public class EmptyAccountTypeException extends Exception {

	public EmptyAccountTypeException() {
		super();
	}

	public EmptyAccountTypeException(String message) {
		super(message);
	}

	public EmptyAccountTypeException(Throwable cause) {
		super(cause);
	}

	public EmptyAccountTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyAccountTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
