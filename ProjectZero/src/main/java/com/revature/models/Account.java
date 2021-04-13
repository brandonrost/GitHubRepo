package com.revature.models;

public class Account extends Client {
	private String accountId; 
	private String clientId;
	private int balance;
	
	public Account() {
		super();
		this.clientId = super.getId(); 
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Account(String accountId, String clientId, int balance) {
		super();
		this.accountId = accountId;
		this.clientId = clientId;
		this.balance = balance;
	}
	
	public Account(String accountId, int balance) {
		super();
		this.accountId = accountId; 
		this.clientId = this.getId(); 
		this.balance = balance;
	}
	
	public Account(String accountId) {
		super();
		this.accountId = accountId; 
		this.clientId = this.getId();
		this.balance = 0;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", clientId=" + clientId + ", balance=" + balance + "]";
	}
	
}
