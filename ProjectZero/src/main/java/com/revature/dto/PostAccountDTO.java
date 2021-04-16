package com.revature.dto;

public class PostAccountDTO {
	
	private String accountType;
	private String accountName = "Default";
	private int balance;
	
	public PostAccountDTO() {
		super(); 
	}
	
	public PostAccountDTO(String accountType, String accountName, int balance) {
		super();
		this.accountType = accountType;
		this.accountName = accountName;
		this.balance = balance;
	} 
	
	public PostAccountDTO(String accountType, int balance) {
		super();
		this.accountType = accountType; 
		this.balance = balance; 
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
}
