package com.revature.exceptions;

public class EmptyClientNameException extends Exception {

	public EmptyClientNameException() {
	}

	public EmptyClientNameException(String message) {
		super(message);
	}

	public EmptyClientNameException(Throwable cause) {
		super(cause);
	}

	public EmptyClientNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyClientNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
