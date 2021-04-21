package com.revature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.exceptions.AccountBalanceNegativeException;
import com.revature.exceptions.AccountDoesNotBelongToClientException;
import com.revature.exceptions.AccountNameNullException;
import com.revature.exceptions.AccountNotAddedException;
import com.revature.exceptions.AccountNotDeletedException;
import com.revature.exceptions.AccountTypeMismatchException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientAlreadyOnAccountException;
import com.revature.exceptions.ClientListNullException;
import com.revature.exceptions.ClientNotAddedException;
import com.revature.exceptions.ClientNotAddedToAccountException;
import com.revature.exceptions.ClientNotDeletedException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.EmptyAccountTypeException;
import com.revature.exceptions.EmptyClientNameException;
import com.revature.exceptions.NoAccountsFoundException;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller {
	private Logger logger = LoggerFactory.getLogger(ExceptionController.class); 
	
	private ExceptionHandler<BadParameterException> badParameterExceptionHandler = (e, ctx)->{
		logger.warn("A user provided a bad parameter. Exception Message: " + e.getMessage());
		ctx.html("Message: User provided bad parameter. Could not satisfy request.");
		ctx.status(400); 
	};
	
	private ExceptionHandler<ClientNotFoundException> clientNotFoundExceptionHandler = (e, ctx)->{
		logger.warn("A user provided a bad parameter. Exception Message: " + e.getMessage()); 
		ctx.html("Message: Client was not found.");
		ctx.status(404); 
	};
	
	private ExceptionHandler<ClientListNullException> clientListNullExceptionHandler = (e, ctx)->{
		logger.warn("A user has requested access to Client table which is currently empty. Exception Message: " + e.getMessage()); 
		ctx.html("Message: Client table is currently empty.");
		ctx.status(400); 
	};
	
	private ExceptionHandler<AccountDoesNotBelongToClientException> accountDoesNotBelongToClientExceptionHandler = (e, ctx)->{
		logger.warn("A user has requested access to an Account that does not belong to them! Exception Message: " + e.getMessage());
		ctx.html("Message: Account does not belong to Client.");
		ctx.status(403); 
	};
	
	private ExceptionHandler<AccountNotAddedException> accountNotAddedExceptionHandler = (e, ctx)->{
		logger.warn("Account was unable to be added to the Database. Exception Message: " + e.getMessage());
		ctx.html("Message: Account not found");
		ctx.status(400); 
	};
	
	private ExceptionHandler<AccountNotDeletedException> accountNotDeletedExceptionHandler = (e, ctx)->{
		logger.warn("Account was unable to be deleted from the Database. Exception Message: " + e.getMessage());
		ctx.html("Message: Account could not be deleted.");
		ctx.status(400);
	};
	
	private ExceptionHandler<AccountTypeMismatchException> accountTypeMismatchExceptionHandler = (e, ctx)->{
		logger.warn("Account Type must either be 'checking' or 'savings'.");
		ctx.html("Message: Account type must be either 'Checking' or 'Savings'.");
		ctx.status(400);
	};
	
	private ExceptionHandler<ClientNotAddedException> clientNotAddedExceptionHandler = (e, ctx)->{
		logger.warn("Client could not be added to the Database. Exception Message: " + e.getMessage());
		ctx.html("Message: Client could not be added.");
		ctx.status(400);
	};
	
	private ExceptionHandler<ClientNotAddedToAccountException> clientNotAddedToAccountExceptionHandler = (e, ctx)->{
		logger.warn("Client could not be added to Account. Exception Message: " + e.getMessage());
		ctx.html("Message: Client couldnt be added.");
		ctx.status(400);
	};
	
	private ExceptionHandler<DatabaseException> databaseExceptionHandler = (e, ctx)->{
		logger.warn("There was an error in your SQL syntax or an error when connecting to Database. Exception Message: " + e.getMessage());
		ctx.html("Message: Problem with database.");
		ctx.status(400);
	};
	
	private ExceptionHandler<EmptyAccountTypeException> emptyAccountTypeExceptionHandler = (e, ctx)->{
		logger.warn("Account Type can not be left null. Please provide a non-empty String for Account_Type.");
		ctx.html("Message: Account Type can not be empty.");
		ctx.status(400);
	};
	
	private ExceptionHandler<EmptyClientNameException> emptyClientNameExceptionHandler = (e, ctx)->{
		logger.warn("Client Name can not be left blank. Please provide a first and last name for Client.");
		ctx.html("Message: Client name can not be empty.");
		ctx.status(400);
	};
	
	private ExceptionHandler<NoAccountsFoundException> noAccountsFoundExceptionHandler = (e, ctx)->{
		logger.warn("No Accounts found for the Client with specified ID. Exception Message: " + e.getMessage());
		ctx.html("Message: No accounts found with with ID.");
		ctx.status(400);
	};

	private ExceptionHandler<ClientNotDeletedException> clientNotDeletedExceptionHandler = (e, ctx)->{
		logger.warn("Client could not be deleted from the Database properly. Exception Message: " + e.getMessage());
		ctx.html("Message: Client could not be deleted.");
		ctx.status(400);
	};
	
	private ExceptionHandler<AccountNameNullException> accountNameNullExceptionHandler = (e, ctx)->{
		logger.warn("Client Name can not be left blank. Exception Message: " + e.getMessage());
		ctx.html("Message: Account name can not be empty.");
		ctx.status(400);
	};
	
	private ExceptionHandler<AccountBalanceNegativeException> accountBalanceNegativeExceptionHandler = (e, ctx)->{
		logger.warn("Account Balance is either Negative or Null. Exception Message: " + e.getMessage());
		ctx.html("Message: Account balance can not be negative.");
		ctx.status(400);
	};
	
	private ExceptionHandler<ClientAlreadyOnAccountException> clientAlreadyOnAccountExceptionHandler = (e, ctx)->{
		logger.warn("Duplicate account-client dependencies. Error Message: " + e.getMessage());
		ctx.html("Message: Client already exists on that account.");
		ctx.status(409);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(BadParameterException.class, badParameterExceptionHandler);
		app.exception(ClientNotFoundException.class, clientNotFoundExceptionHandler);
		app.exception(ClientListNullException.class, clientListNullExceptionHandler); 
		app.exception(AccountDoesNotBelongToClientException.class, accountDoesNotBelongToClientExceptionHandler); 
		app.exception(AccountNotAddedException.class, accountNotAddedExceptionHandler); 
		app.exception(AccountNotDeletedException.class, accountNotDeletedExceptionHandler);   
		app.exception(AccountTypeMismatchException.class,  accountTypeMismatchExceptionHandler);
		app.exception(ClientNotAddedException.class, clientNotAddedExceptionHandler); 
		app.exception(ClientNotAddedToAccountException.class,clientNotAddedToAccountExceptionHandler);
		app.exception(DatabaseException.class, databaseExceptionHandler); 
		app.exception(EmptyAccountTypeException.class, emptyAccountTypeExceptionHandler);
		app.exception(EmptyClientNameException.class, emptyClientNameExceptionHandler);
		app.exception(NoAccountsFoundException.class, noAccountsFoundExceptionHandler);
		app.exception(ClientNotDeletedException.class, clientNotDeletedExceptionHandler);
		app.exception(AccountNameNullException.class, accountNameNullExceptionHandler);
		app.exception(AccountBalanceNegativeException.class, accountBalanceNegativeExceptionHandler); 
		app.exception(ClientAlreadyOnAccountException.class, clientAlreadyOnAccountExceptionHandler); 
	}

}
