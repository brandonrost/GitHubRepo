package com.revature.exceptions;

public class ClientAlreadyOnAccountException extends Exception {

	public ClientAlreadyOnAccountException() {
		super();
	}

	public ClientAlreadyOnAccountException(String message) {
		super(message);
	}

	public ClientAlreadyOnAccountException(Throwable cause) {
		super(cause);
	}

	public ClientAlreadyOnAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientAlreadyOnAccountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
