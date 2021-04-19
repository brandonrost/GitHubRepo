package com.revature.exceptions;

public class AccountBalanceNegativeException extends Exception {

	public AccountBalanceNegativeException() {
		super();
	}

	public AccountBalanceNegativeException(String message) {
		super(message);
	}

	public AccountBalanceNegativeException(Throwable cause) {
		super(cause);
	}

	public AccountBalanceNegativeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountBalanceNegativeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
