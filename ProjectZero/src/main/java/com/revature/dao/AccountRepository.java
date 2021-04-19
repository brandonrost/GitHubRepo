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
import com.revature.exceptions.AccountNotDeletedException;
import com.revature.exceptions.ClientNotAddedToAccountException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.NoAccountsFoundException;
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

	public Account addAccount(String clientID, PostAccountDTO accountDTO)
			throws AccountNotAddedException, DatabaseException {
		logger.info("Connecting to database inside the " + this.getClass());
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

			if (rs.next() == false) {
				throw new AccountNotAddedException("Could not add Account to the Database.");
			}

			rs.beforeFirst();
			int account_id;
			if (rs.next()) {
				account_id = rs.getInt(1);
			} else {
				throw new DatabaseException(
						"AccountID was not generated; therefore, Client was not successfully added.");
			}

			String addToClient_AccountSQL = "INSERT INTO client_account(client_id,account_id)" + "VALUES(?,?);";
			PreparedStatement pstmt2 = connection.prepareStatement(addToClient_AccountSQL,
					Statement.RETURN_GENERATED_KEYS);
			pstmt2.setInt(1, Integer.valueOf(clientID));
			pstmt2.setInt(2, account_id);

			int addToClient_AccountSQL_rs = pstmt2.executeUpdate();
			if (addToClient_AccountSQL_rs != 1) {
				throw new DatabaseException("Account failed to attach to client Object.");
			}
			logger.info("Executed SQL Statment: " + addToClient_AccountSQL);

			Account account = new Account(String.valueOf(account_id), accountDTO.getAccountType(),
					accountDTO.getAccountName(), accountDTO.getBalance());
			return account;

		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong with the database. " + "Exception message: " + e.getMessage());
		}
	}

	public ArrayList<Account> getAccounts(String clientID) throws NoAccountsFoundException, DatabaseException {
		logger.info("Connecting to database inside the " + this.getClass());
		try {
			String sql = "SELECT a.id AS AccountID, a.account_type AS AccountType, a.account_type AS AccountName, a.account_balance AS Balance "
					+ "FROM account AS a " + "LEFT JOIN client_account AS ca ON a.id = ca.account_id "
					+ "LEFT JOIN client AS c ON c.id = ca.client_id " + "WHERE c.id = ?;";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, Integer.valueOf(clientID));

			boolean recordsQueried = pstmt.execute();
			logger.info("Executed SQL Statment: " + sql);

			if (recordsQueried == false) {
				throw new DatabaseException();
			}

			ResultSet rs = pstmt.getResultSet();

			ArrayList<Account> client_accounts = new ArrayList<>();

			while (rs.next()) {
				String account_id = String.valueOf(rs.getInt(1));
				String account_type = rs.getString(2);
				String account_name = rs.getString(3);
				int account_balance = rs.getInt(4);
				client_accounts.add(new Account(account_id, account_type, account_name, account_balance));
			}

			return client_accounts;

		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the database. Exception Message: " + e.getMessage());
		} catch (DatabaseException e2) {
			throw new NoAccountsFoundException(
					"Could not find any accounts in the Database belonging to client with ID of '" + clientID + "'.");
		}
	}

	public ArrayList<Account> getAccountByBalance(String clientID, String greaterAmount, String lesserAmount) throws NoAccountsFoundException, DatabaseException {
		logger.info("Connecting to database inside the " + this.getClass());
		try {
			String sqlBoth = "SELECT a.id AS AccountID, a.account_type AS AccountType, a.account_type AS AccountName, a.account_balance AS Balance "
					+ "FROM account AS a " + "LEFT JOIN client_account AS ca ON a.id = ca.account_id "
					+ "LEFT JOIN client AS c ON c.id = ca.client_id " + "WHERE c.id = ? HAVING a.account_balance > ? AND a.account_balance < ?;";
			
			String sqlGreaterThan = "SELECT a.id AS AccountID, a.account_type AS AccountType, a.account_type AS AccountName, a.account_balance AS Balance "
					+ "FROM account AS a " + "LEFT JOIN client_account AS ca ON a.id = ca.account_id "
					+ "LEFT JOIN client AS c ON c.id = ca.client_id " + "WHERE c.id = ? HAVING a.account_balance < ?;";
			
			String sqlLessThan = "SELECT a.id AS AccountID, a.account_type AS AccountType, a.account_type AS AccountName, a.account_balance AS Balance "
					+ "FROM account AS a " + "LEFT JOIN client_account AS ca ON a.id = ca.account_id "
					+ "LEFT JOIN client AS c ON c.id = ca.client_id " + "WHERE c.id = ? HAVING a.account_balance > ?;";
			
			PreparedStatement pstmt; 			
			if(greaterAmount != "0" && lesserAmount != "0") {
				logger.info("Preparing SQL Statement with both parameters filled in.");
				pstmt = connection.prepareStatement(sqlBoth, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, Integer.valueOf(clientID));
				pstmt.setInt(2, Integer.valueOf(lesserAmount));
				pstmt.setInt(3, Integer.valueOf(greaterAmount));
				
			}else {
				if(greaterAmount != "0") {
					logger.info("Preparing SQL Statement with less than parameters filled in.");
					pstmt = connection.prepareStatement(sqlGreaterThan, Statement.RETURN_GENERATED_KEYS);
					pstmt.setInt(1, Integer.valueOf(clientID));
					pstmt.setInt(2, Integer.valueOf(greaterAmount));
				}else {
					logger.info("Preparing SQL Statement with greater than parameters filled in.");
					pstmt = connection.prepareStatement(sqlLessThan, Statement.RETURN_GENERATED_KEYS);
					pstmt.setInt(1, Integer.valueOf(clientID));
					pstmt.setInt(2, Integer.valueOf(lesserAmount));
				}
			}

			boolean recordsQueried = pstmt.execute();
			logger.info("Executed SQL Statment: SELECT account WHERE account_balance < greaterAmount AND account_balance > lesserAmount;");

			if (recordsQueried == false) {
				throw new DatabaseException();
			}

			ResultSet rs = pstmt.getResultSet();

			ArrayList<Account> client_accounts = new ArrayList<>();

			while (rs.next()) {
				String account_id = String.valueOf(rs.getInt(1));
				String account_type = rs.getString(2);
				String account_name = rs.getString(3);
				int account_balance = rs.getInt(4);
				client_accounts.add(new Account(account_id, account_type, account_name, account_balance));
			}

			return client_accounts;

		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the database. Exception Message: " + e.getMessage());
		} catch (DatabaseException e2) {
			throw new NoAccountsFoundException("Could not find any accounts in the Database belonging to client with ID of '" + clientID + "'.");
		}
	}

	public Account getAccount(String clientID, String accountID) throws DatabaseException, NoAccountsFoundException {
		logger.info("Connecting to database inside the " + this.getClass());
		try {
			String sql = "SELECT a.id AS AccountID, a.account_type AS AccountType, a.account_type AS AccountName, a.account_balance AS Balance "
					+ "FROM account AS a " + "LEFT JOIN client_account AS ca ON a.id = ca.account_id "
					+ "LEFT JOIN client AS c ON c.id = ca.client_id " + "WHERE c.id = ? AND a.id = ?;";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, Integer.valueOf(clientID));
			pstmt.setInt(2, Integer.valueOf(accountID));

			boolean recordsQueried = pstmt.execute();
			logger.info("Executed SQL Statment: " + sql);

			if (recordsQueried == false) {
				throw new DatabaseException();
			}

			ResultSet rs = pstmt.getResultSet();

			Account client_account = new Account();

			if (rs.next()) {
				client_account.setAccountId(String.valueOf(rs.getInt(1)));
				client_account.setAccountType(rs.getString(2));
				client_account.setAccountName(rs.getString(3));
				client_account.setBalance(rs.getInt(4));
			}
			return client_account;

		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the database. Exception Message: " + e.getMessage());
		} catch (DatabaseException e2) {
			throw new NoAccountsFoundException(
					"Could not find any accounts in the Database belonging to client with ID of '" + clientID + "'.");
		}
	}

	public Account updateAccount(String clientID, String accountID, PostAccountDTO accountToBeUpdated) throws DatabaseException {
		logger.info("Accessing the database through the " + this.getClass());
		try {
			String sql = "UPDATE account SET account_type = ?, account_name = ?, account_balance = ? WHERE account.id = ?";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, accountToBeUpdated.getAccountType());
			pstmt.setString(2, accountToBeUpdated.getAccountName());
			pstmt.setInt(3, accountToBeUpdated.getBalance());
			pstmt.setInt(4, Integer.valueOf(accountID));

			int recordsAdded = pstmt.executeUpdate();

			if (recordsAdded == 0) {
				throw new DatabaseException("Could not update Account with new information.");
			}

			Account account = new Account(accountID, accountToBeUpdated.getAccountType(),
					accountToBeUpdated.getAccountName(), accountToBeUpdated.getBalance());

			logger.info("Executed SQL Statement: " + sql);

			return account;

		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong with the database query. Exception Message: " + e.getMessage());
		}
	}

	public Account deleteAccount(String clientID, String accountID) throws DatabaseException {
		logger.info("Accessing the database through the " + this.getClass());
		try {
			String sql = "SELECT * FROM account WHERE account.id = ?;";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, Integer.valueOf(accountID));

			boolean recordsAdded = pstmt.execute();

			if (recordsAdded == false) {
				throw new NoAccountsFoundException();
			}

			logger.info("Executed SQL Statement: " + sql);

			ResultSet rs = pstmt.getResultSet();

			Account account = new Account();
			if (rs.next()) {
				account.setAccountId(String.valueOf(rs.getInt(1)));
				account.setAccountType(rs.getString(2));
				account.setAccountName(rs.getString(3));
				account.setBalance(rs.getInt(4));
			} else {
				throw new DatabaseException();
			}

			String sql2 = "DELETE FROM account WHERE account.id = ?;";

			PreparedStatement pstmt2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			pstmt2.setInt(1, Integer.valueOf(accountID));

			int recordsAdded2 = pstmt2.executeUpdate();

			if (recordsAdded2 == 0) {
				throw new AccountNotDeletedException("Could not delete Account from the 'account' table.");
			}

			logger.info("Executed SQL Statement: " + sql2);

			return account;

		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong with the database query. Exception Message: " + e.getMessage());
		} catch (NoAccountsFoundException e2) {
			throw new DatabaseException(
					"Could not find an account in the database with the ID of '" + accountID + "'.");
		} catch (AccountNotDeletedException e3) {
			throw new DatabaseException("Could not delete Account from 'account' table.");
		}
	}

	public void addClientToAccount(String accountID, String clientToBeAddedID) throws DatabaseException, ClientNotAddedToAccountException {
		logger.info("Accessing the database through the " + this.getClass());
		try {
			String sql = "INSERT INTO client_account(client_id, account_id) VALUES (?, ?);";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(2, Integer.valueOf(accountID));
			pstmt.setInt(1, Integer.valueOf(clientToBeAddedID));

			int recordsAdded = pstmt.executeUpdate(); 

			if (recordsAdded == 0) {
				throw new DatabaseException("Could not add Client to Account.");
			}

			logger.info("Executed SQL Statement: " + sql);

		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with the database query. Exception Message: " + e.getMessage());
		} catch (DatabaseException e1) {
			throw new ClientNotAddedToAccountException("Client with ID of '" + clientToBeAddedID + "' could not be added to the Account with ID of '"+ accountID + "'.");
		}
	}

}
