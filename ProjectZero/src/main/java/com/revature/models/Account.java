package com.revature.models;

public class Account {
	private String accountId; 
	private String accountType;
	private String accountName;
	private int balance;
	
	public Account() {
		super(); 
	}
	
	public Account(String accountType, int balance) {
		super();
		this.accountType = accountType;
		this.balance = balance; 
	}
	
	public Account(String accountType, String accountName, int balance) {
		super();
		this.accountType = accountType;
		this.accountName = accountName;
		this.balance = balance; 
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountType=" + accountType + ", accountName=" + accountName
				+ ", balance=" + balance + "]";
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + balance;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (balance != other.balance)
			return false;
		return true;
	}
	
}
