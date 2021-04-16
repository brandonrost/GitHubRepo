package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.AccountRepository;
import com.revature.dto.PostAccountDTO;
import com.revature.models.Account;

public class AccountService {
	
	private AccountRepository accountRepository; 
	
	public AccountService() {
		super();
		this.accountRepository = new AccountRepository(); 
	}
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository; 
	}
	
	public Account addAccount(String clientID, PostAccountDTO accountDTO) {
		return accountRepository.addAccount(clientID, accountDTO); 
	}

	public ArrayList<Account> getAccounts(String clientID) {
		return accountRepository.getAccounts(clientID); 
	}

	public ArrayList<Account> getAccountByBalance(String clientID, String greaterAmount, String lesserAmount) {
		// TODO Auto-generated method stub
		return accountRepository.getAccountByBalance(clientID, greaterAmount, lesserAmount);
	}

	public Account getAccount(String clientID, String accountID) {
		// TODO Auto-generated method stub
		return accountRepository.getAccount(clientID, accountID);
	}

	public Account updateAccount(String clientID, String accountID, PostAccountDTO accountToBeUpdated) {
		// TODO Auto-generated method stub
		return accountRepository.updateAccount(clientID, accountID, accountToBeUpdated);
	}

	public Account deleteAccount(String clientID, String accountID) {
		System.out.println(clientID + " " + accountID);
		// TODO Auto-generated method stub
		return accountRepository.deleteAccount(clientID, accountID); 
	}

}
