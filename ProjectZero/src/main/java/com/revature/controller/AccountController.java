package com.revature.controller;

import java.util.ArrayList;

import com.revature.dto.PostAccountDTO;
import com.revature.models.Account;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller {
	
	private AccountService accountService; 
	
	public AccountController() {
		this.accountService = new AccountService(); 
	}

	private Handler addAccount = ctx ->{
		String clientID = ctx.pathParam("clientid");
		
		PostAccountDTO accountDTO = ctx.bodyAsClass(PostAccountDTO.class); 
		
		Account account = this.accountService.addAccount(clientID, accountDTO); 
		
		ctx.json(account); 
		ctx.status(201); 
	};
	
	private Handler getAccounts = ctx ->{
		String clientID = ctx.pathParam("clientid");
		
		String amtlessthan = "amountLessThan"; 
		String amtgreaterthan = "amountGreaterThan";		
		
		
		if((ctx.queryParam(amtlessthan)==null)&& (ctx.queryParam(amtgreaterthan)==null)){
			//what to do if there are no query params
			System.out.println("You do not have any query parameters being used");
			ArrayList<Account> accounts = accountService.getAccounts(clientID); 
			ctx.json(accounts); 
			ctx.status(200); 
		}else {		
			String greaterAmount = "0"; 
			String lesserAmount = "0";
			
			if(ctx.queryParam(amtlessthan)!=null) greaterAmount = ctx.queryParam("amountLessThan"); 			
			if(ctx.queryParam(amtgreaterthan)!=null) lesserAmount = ctx.queryParam("amountGreaterThan"); 
			
			ArrayList<Account> accounts = accountService.getAccountByBalance(clientID, greaterAmount, lesserAmount); 
			
			ctx.json(accounts);
			ctx.status(200); 
		}
	};
	
	
	private Handler getAccount = ctx->{
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");
		
		Account account = accountService.getAccount(clientID, accountID); 
		
		ctx.json(account);
		ctx.status(200); 
		
	};
	
	private Handler updateAccount = ctx->{
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");
		
		PostAccountDTO accountToBeUpdated = ctx.bodyAsClass(PostAccountDTO.class); 
		
		Account updatedAccount = accountService.updateAccount(clientID, accountID, accountToBeUpdated); 
		
		ctx.json(accountToBeUpdated);
		ctx.json(updatedAccount); 
		ctx.status(200); 
	};
	
	private Handler deleteAccount = ctx->{
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");
		
		Account account = accountService.deleteAccount(clientID, accountID); 
		
		ctx.json(account);
		ctx.status(200); 
		
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/clients/:clientid/accounts", addAccount); //		POST /clients/{client_id}/accounts: Create a new account for a client with id of X (if client exists)
		app.get("/clients/:clientid/accounts", getAccounts); //		GET /clients/{client_id}/accounts: Get all accounts for client with id of X (if client exists)
		//The above also accomplishes this > if query params are added //		GET /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400: Get all accounts for client id of X with balances between 400 and 2000 (if client exists)
		app.get("/clients/:clientid/accounts/:accountid", getAccount); //		GET /clients/{client_id}/accounts/{account_id}: Get account with id of Y belonging to client with id of X (if client and account exist AND if account belongs to client)
		app.put("/clients/:clientid/accounts/:accountid", updateAccount);//		PUT /clients/{client_id}/accounts/{account_id}: Update account with id of Y belonging to client with id of X (if client and account exist AND if account belongs to client)
		app.delete("/clients/:clientid/accounts/:accountid", deleteAccount); //		DELETE /clients/{client_id}/accounts/{account_id}: Delete account with id of Y belonging to client with id of X (if client and account exist AND if account belongs to client)

	}

}
