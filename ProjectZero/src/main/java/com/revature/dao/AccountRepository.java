package com.revature.dao;

import java.util.ArrayList;

import com.revature.dto.PostAccountDTO;
import com.revature.models.Account;

public class AccountRepository {
	
	public Account addAccount(String clientID, PostAccountDTO accountDTO) {
		Account account = new Account(accountDTO.getAccountType(), accountDTO.getAccountName(), accountDTO.getBalance()); 
		return account; 
	}

	public ArrayList<Account> getAccounts(String clientID) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account("Savings","MySavingsAccount",10));
		accounts.add(new Account("Checking","MySecondChecking", 100)); 
		return accounts; 
	}

	public ArrayList<Account> getAccountByBalance(String clientID, String greaterAmount, String lesserAmount) {
		ArrayList<Account> accounts = new ArrayList<Account>(); 
		if(greaterAmount == "") {
			greaterAmount= "0"; 
		}
		if(lesserAmount == "") {
			lesserAmount = "0";
		}
		accounts.add(new Account("Savings","2ndAccount",(Integer.valueOf(greaterAmount) - 10)));
		accounts.add(new Account("Checking","Checking Account", (Integer.valueOf(lesserAmount)+10))); 
		return accounts;
	}

	public Account getAccount(String clientID, String accountID) {
		Account account = new Account("Savings", "getAccount", 100); 
		return account; 
	}

	public Account updateAccount(String clientID, String accountID, PostAccountDTO accountToBeUpdated) {
		Account updatedAccount = new Account(accountToBeUpdated.getAccountType(), accountToBeUpdated.getAccountName(), accountToBeUpdated.getBalance()); 
		return updatedAccount;
	}

	public Account deleteAccount(String clientID, String accountID) {
		Account deletedAccount; 
		if(Integer.valueOf(clientID)==1 && Integer.valueOf(accountID)==1) {
			deletedAccount = new Account("Checking","DeletedAccount", 10000); 
		}else {
			deletedAccount = new Account("Checking","AccountNotDeleted",10); 
		}
		return deletedAccount;
	}

}
