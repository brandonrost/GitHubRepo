package com.revature.exceptions;

public class ClientListNullException extends Exception {

	public ClientListNullException() {
		super(); 
	}

	public ClientListNullException(String message) {
		super(message);
	}

	public ClientListNullException(Throwable cause) {
		super(cause);
	}

	public ClientListNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientListNullException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
