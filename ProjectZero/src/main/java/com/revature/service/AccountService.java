package com.revature.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountRepository;
import com.revature.dao.ClientRepository;
import com.revature.dto.PostAccountDTO;
import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.util.ConnectionUtil;
import com.revature.exceptions.AccountNotAddedException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.EmptyAccountTypeException;
import com.revature.exceptions.EmptyClientNameException;

public class AccountService {
	
	private Logger logger = LoggerFactory.getLogger(AccountService.class); 	
	private AccountRepository accountRepository; 
	private ClientRepository clientRepository;
	
	public AccountService() {
		super();
		this.accountRepository = new AccountRepository(); 
		this.clientRepository = new ClientRepository();
	}
		
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
		this.clientRepository = new ClientRepository(); 
	}
	
	

	
	public Client addAccount(String clientID, PostAccountDTO accountDTO) throws DatabaseException, BadParameterException, EmptyAccountTypeException, AccountNotAddedException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			connection.setAutoCommit(false);
			
			int client_id = Integer.parseInt(clientID); 
			
			if (accountDTO.getAccountType().trim().strip()=="") {
				throw new EmptyAccountTypeException("Account Type can not be empty."); 
			}
			
			//Client client = clientRepository.getClientById(client_id);// need to add functionality before this works
			Client client = new Client(clientID, "Tom", "Cruise"); //for now use this
			Account account = accountRepository.addAccount(clientID, accountDTO); 
			
			client.addAccount(account);			
			connection.commit();
			return client;
			
		} catch(SQLException e1) {
			throw new DatabaseException("Something went wrong when trying to get a connection. " + "Exception message: " + e1.getMessage()); 
		} catch(NumberFormatException e2) {
			throw new BadParameterException("Bad parameter given. ClientID must be of type 'int' user gave '" + clientID + "'. Error Message: " + e2.getMessage());
		}
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
		// TODO Auto-generated method stub
		return accountRepository.deleteAccount(clientID,accountID);
	}

}
