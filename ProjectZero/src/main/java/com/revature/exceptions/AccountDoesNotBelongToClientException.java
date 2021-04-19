package com.revature.exceptions;

public class AccountDoesNotBelongToClientException extends Exception {

	public AccountDoesNotBelongToClientException() {
		super();
	}

	public AccountDoesNotBelongToClientException(String message) {
		super(message);
	}

	public AccountDoesNotBelongToClientException(Throwable cause) {
		super(cause);
	}

	public AccountDoesNotBelongToClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountDoesNotBelongToClientException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
