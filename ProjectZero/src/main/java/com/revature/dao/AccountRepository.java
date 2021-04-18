package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.PostAccountDTO;
import com.revature.exceptions.AccountNotAddedException;
import com.revature.exceptions.DatabaseException;
import com.revature.models.Account;
import com.revature.models.Client;

public class AccountRepository {
	private Logger logger = LoggerFactory.getLogger(AccountRepository.class); 
	private Connection connection;

	public AccountRepository() {
		super();
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	
	public Account addAccount(String clientID, PostAccountDTO accountDTO) throws AccountNotAddedException, DatabaseException{
		logger.info("Connecting to database inside the "+ this.getClass());
		try {
			String sql = "INSERT INTO account (account_type, account_name, account_balance) VALUES (?,?,?);";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, accountDTO.getAccountType());
			pstmt.setString(2, accountDTO.getAccountName());
			pstmt.setInt(3, accountDTO.getBalance());

			int recordsAdded = pstmt.executeUpdate();
			logger.info("Executed SQL Statment: " + sql);

			if (recordsAdded != 1) {
				throw new DatabaseException("Couldn't add Account to the Database.");
			}
			
			String getAccountIDSQL = "SELECT LAST_INSERT_ID();";
			
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery(getAccountIDSQL);
			logger.info("Executed SQL Statment: " + getAccountIDSQL);
			
			if(rs.next()==false) {
				throw new AccountNotAddedException("Could not add Account to the Database."); 
			}
			
			rs.beforeFirst();
			int account_id;
			if (rs.next()) {
				account_id = rs.getInt(1);
			} else {
				throw new DatabaseException("AccountID was not generated; therefore, Client was not successfully added.");
			}
			
			String addToClient_AccountSQL = "INSERT INTO client_account(client_id,account_id)"
					+ "VALUES(?,?);";
			PreparedStatement pstmt2 = connection.prepareStatement(addToClient_AccountSQL, Statement.RETURN_GENERATED_KEYS); 
			pstmt2.setInt(1, Integer.valueOf(clientID));
			pstmt2.setInt(2, account_id);
			
			int addToClient_AccountSQL_rs = pstmt2.executeUpdate(); 
			if(addToClient_AccountSQL_rs != 1) {
				throw new DatabaseException("Account failed to attach to client Object.");
			}
			logger.info("Executed SQL Statment: " + addToClient_AccountSQL);
			
			Account account = new Account(String.valueOf(account_id), accountDTO.getAccountType(), accountDTO.getAccountName(), accountDTO.getBalance()); 
			return account; 

		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with the database. " + "Exception message: " + e.getMessage());
		}
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
