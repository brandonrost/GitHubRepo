package com.revature.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountRepository;
import com.revature.dao.ClientRepository;
import com.revature.dto.AddClientToAccountDTO;
import com.revature.dto.PostAccountDTO;
import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.util.ConnectionUtil;
import com.revature.exceptions.AccountBalanceNegativeException;
import com.revature.exceptions.AccountDoesNotBelongToClientException;
import com.revature.exceptions.AccountNameNullException;
import com.revature.exceptions.AccountNotAddedException;
import com.revature.exceptions.AccountTypeMismatchException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientAlreadyOnAccountException;
import com.revature.exceptions.ClientNotAddedToAccountException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.EmptyAccountTypeException;
import com.revature.exceptions.EmptyClientNameException;
import com.revature.exceptions.NoAccountsFoundException;

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

	
	public Client addAccount(String clientID, PostAccountDTO accountDTO) throws DatabaseException, BadParameterException, EmptyAccountTypeException, AccountNotAddedException, ClientNotFoundException, AccountTypeMismatchException, AccountNameNullException, AccountBalanceNegativeException {
		logger.info("Curently performing business logic inside the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int client_id = Integer.parseInt(clientID); 
			
			if (accountDTO.getAccountType().trim().strip()=="") {
				throw new EmptyAccountTypeException("Account Type can not be empty."); 
			}
			
			if (accountDTO.getAccountName().trim().strip() == "")throw new AccountNameNullException("Account could not be added because Account Name was null."); 
			
			boolean accountTypeWrong = true; 			
			if(accountDTO.getAccountType().trim().strip().equals("Savings") || accountDTO.getAccountType().equals("Checking")) {
				accountTypeWrong = false;  
			}
			
			if(accountTypeWrong)throw new AccountTypeMismatchException("Account Type must be either 'Savings' or 'Checking'. User provided " + accountDTO.getAccountType()); 
			
			if(accountDTO.getBalance() < 0) throw new AccountBalanceNegativeException("Account can not be created with an negative or null balance.");
			
			
			Client client = clientRepository.getClientById(client_id);
			
			Account account = accountRepository.addAccount(clientID, accountDTO); 
			connection.commit();
			
			logger.info("SQL Query Success! Now back inside the " + this.getClass());
	
			client.addAccount(account);	
			
			return client;
			
		} catch(SQLException e1) {
			throw new DatabaseException("Something went wrong when trying to get a connection. " + "Exception message: " + e1.getMessage()); 
		} catch(NumberFormatException e2) {
			throw new BadParameterException("Bad parameter given. ClientID must be of type 'int' user gave '" + clientID + "'. Error Message: " + e2.getMessage());
		}
	}

	public Client getAccounts(String clientID) throws DatabaseException, BadParameterException, NoAccountsFoundException, ClientNotFoundException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int client_id = Integer.parseInt(clientID); 
			
			Client client = clientRepository.getClientById(client_id); 						
			ArrayList<Account> client_accounts = accountRepository.getAccounts(clientID); 
			connection.commit();
			
			client.setAccounts(client_accounts);
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client id must be of type 'int'. User provided '" + clientID +"'."); 
		}
	}

	public Client getAccountByBalance(String clientID, String greaterAmount, String lesserAmount) throws DatabaseException, BadParameterException, NoAccountsFoundException, ClientNotFoundException {
		logger.info("Curently performing business logic inside the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int client_id = Integer.parseInt(clientID); 
			
			if(greaterAmount != "" && lesserAmount != "") {
				try {
					int greaterAmountint = Integer.parseInt(greaterAmount); 
					int lesserAmountint = Integer.parseInt(lesserAmount); 
				}catch (NumberFormatException e) {
					throw new BadParameterException("'Greater than' and 'less than' parameters need to be of type 'int'. User provided 'greater than' = '" 
													+ greaterAmount + "', 'less than' = '"+lesserAmount +"'."); 
				}
				if(Integer.valueOf(greaterAmount) < Integer.valueOf(lesserAmount)) {
					throw new BadParameterException("'Less Than' amount needs to be greater than the 'Greater Than' amount. " + greaterAmount + " is not > (greater than) " + lesserAmount + "."); 
				}
			}else {
				if(greaterAmount == "")greaterAmount = "0"; 
				if(lesserAmount == "")lesserAmount = "0"; 							
			}
			
			
			
			Client client = clientRepository.getClientById(client_id); 						
			ArrayList<Account> client_accounts = accountRepository.getAccountByBalance(clientID, greaterAmount, lesserAmount); 
			connection.commit();
			logger.info("SQL Query Success! Now back inside the " + this.getClass());
			
			client.setAccounts(new ArrayList<Account>());
			client.setAccounts(client_accounts);
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client id must be of type 'int'. User provided '" + clientID +"'."); 
		}
	}

	public Client getAccount(String clientID, String accountID) throws DatabaseException, BadParameterException, NoAccountsFoundException, AccountDoesNotBelongToClientException, ClientNotFoundException {
		logger.info("Curently performing business logic inside the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int client_id = Integer.parseInt(clientID);
			int account_id = Integer.parseInt(accountID); 
			
			Client client = clientRepository.getClientById(client_id);
			ArrayList<Account> client_accounts = client.getAccounts(); 
			boolean ownsAccount = false; 
			
			for(Account account:client_accounts) {
				if(account.getAccountId().equals(accountID) && ownsAccount == false) {
					ownsAccount = true;
				}
			}			
			if(ownsAccount == true) {
				Account client_account = accountRepository.getAccount(clientID , accountID); 
				connection.commit();				
				client.setAccounts(new ArrayList<Account>());				
				client.addAccount(client_account);
				logger.info("SQL Query Success! Now back inside the " + this.getClass());
			}else {
				throw new AccountDoesNotBelongToClientException("Account with ID of '"+ accountID + "' does not belong to Client with ID of '" + clientID +"'."); 
			}
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID and Account ID must be of type 'int'. User provided client ID: '" + clientID +"' and account ID: '" + accountID + "'."); 
		} 
	}

	public Client updateAccount(String clientID, String accountID, PostAccountDTO accountToBeUpdated) throws DatabaseException, BadParameterException, AccountTypeMismatchException, AccountDoesNotBelongToClientException, ClientNotFoundException {
		logger.info("Curently performing business logic inside the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int id = Integer.parseInt(clientID);
			int account_id = Integer.parseInt(accountID); 
			
			boolean accountTypeWrong = true; 			
			if(accountToBeUpdated.getAccountType().trim().strip().equals("Savings") || accountToBeUpdated.getAccountType().equals("Checking")) {
				accountTypeWrong = false;  
			}
			
			if(accountTypeWrong)throw new AccountTypeMismatchException("Account Type must be of type 'Checking' or 'Savings'. User entered " + accountToBeUpdated.getAccountType());
			
			Client client = clientRepository.getClientById(id); 
			ArrayList<Account> client_accounts = client.getAccounts(); 
			boolean ownsAccount = false; 
			
			for(Account account:client_accounts) {
				if(account.getAccountId().equals(accountID) && ownsAccount == false) {
					ownsAccount = true;
				}
			}
			
			if(ownsAccount == true) {
				Account account = accountRepository.updateAccount(clientID, accountID, accountToBeUpdated); 				
				connection.commit();				
				client.setAccounts(new ArrayList<Account>());				
				client.addAccount(account);
				logger.info("SQL Query Success! Now back inside the " + this.getClass());
			}else {
				throw new AccountDoesNotBelongToClientException("Account with ID of '"+ accountID +"' does not belong to Client with ID of '"+ clientID + "'.");  
			}
						
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the Database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID and Account ID must be of type (int). User provided '" + clientID + "' and '" + accountID + "'."); 
		}
	}

	public Client deleteAccount(String clientID, String accountID) throws DatabaseException, BadParameterException, ClientNotFoundException, AccountDoesNotBelongToClientException {
		logger.info("Curently performing business logic inside the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int id = Integer.parseInt(clientID);
			int account_id = Integer.parseInt(accountID); 
			
			Client client = clientRepository.getClientById(id); 
			ArrayList<Account> client_accounts = client.getAccounts(); 
			boolean ownsAccount = false; 
			
			for(Account account:client_accounts) {
				if(account.getAccountId().equals(accountID) && ownsAccount == false) {
					ownsAccount = true;
				}
			}			
			
			if(ownsAccount == true) {
				Account account = accountRepository.deleteAccount(clientID, accountID); 	
				logger.info("SQL Query Success! Now back inside the " + this.getClass());
				connection.commit();				
				client.setAccounts(new ArrayList<Account>());				
				client.addAccount(account);
			}else {
				throw new AccountDoesNotBelongToClientException("Account with ID of '" + accountID + "' does not belong to Client with ID of '" + clientID +"'.");
			}	
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the Database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID and Account ID must be of type (int). User provided client_id:'" + clientID + "' and account_id:'" + accountID + "'."); 
		} 
	}

	public Client addClientToAccount(String clientID, String accountID, AddClientToAccountDTO clientToBeAdded) throws DatabaseException, ClientNotAddedToAccountException, BadParameterException, AccountDoesNotBelongToClientException, ClientNotFoundException, ClientAlreadyOnAccountException {
		logger.info("Curently performing business logic inside the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int client_id = Integer.parseInt(clientID);
			int account_id = Integer.parseInt(accountID); 
						
			Client client = clientRepository.getClientById(client_id); 
			ArrayList<Account> client_accounts = client.getAccounts(); 
			boolean ownsAccount = false; 
			
			for(Account account:client_accounts) {
				if(account.getAccountId().equals(accountID) && ownsAccount == false) {
					ownsAccount = true;
				}
			}
			
			Client addedClient; 
			if(ownsAccount == true) {
				try {
					addedClient = clientRepository.getClientById(Integer.valueOf(clientToBeAdded.getClientID())); 
					logger.info("Account belongs to Client with ID of '"+clientID+"'.");
					boolean onAccount = false;
					ArrayList<Account> added_client_accounts = addedClient.getAccounts(); 
					for(Account account:added_client_accounts) {
						if(account.getAccountId().equals(accountID)) {
							onAccount = true; 
						}
					}
					if(onAccount == false) {
						accountRepository.addClientToAccount(accountID, clientToBeAdded.getClientID());
						connection.commit();
					} else {
						throw new ClientAlreadyOnAccountException("Client with ID of '" + addedClient.getId() + "' already listed on that account.");
					}
				}catch(DatabaseException e) {
					throw new ClientNotFoundException("Could not find client with ID of '"+ clientToBeAdded.getClientID() + "' in the database."); 
				}					
			}else {
				throw new AccountDoesNotBelongToClientException("Account with ID of '"+ accountID +"' does not belong to Client with ID of '"+ clientID + "'."); 
			}
						
			return addedClient; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the Database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID and Account ID must be of type (int). User provided client_1_id: '" + clientID + "', client_2_id: '"+ clientToBeAdded.getClientID() +"', and account_id: '" + accountID + "'."); 
		}
	}

}
