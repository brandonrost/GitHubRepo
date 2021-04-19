package com.revature.controller;

import java.util.ArrayList;

import com.revature.dto.PostAccountDTO;
import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller {

	private AccountService accountService;

	public AccountController() {
		this.accountService = new AccountService();
	}

	private Handler addAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");

		PostAccountDTO accountDTO = ctx.bodyAsClass(PostAccountDTO.class);

		Client client = this.accountService.addAccount(clientID, accountDTO);

		if (client == null)
			ctx.status(400);

		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<h1>Added Account</h1>\n");
		htmlString.append("<p><h3>ClientName: " + client.getFirstName() + " " + client.getLastName() + "</h3></p>\n");
		
		Account client_account = client.getAccounts().get(client.getAccounts().size()-1); 
		htmlString.append("<p>Account Type: " + client_account.getAccountType() + "</p>" + "<p>Account Name: "
				+ client_account.getAccountName() + "</p>" + "<p>Account Balance: " + client_account.getBalance()
				+ "</p>");

		ctx.html(htmlString.toString());
		ctx.status(201);
	};

	private Handler getAccounts = ctx -> {
		String clientID = ctx.pathParam("clientid");

		String greaterAmount = "";
		String lesserAmount = "";

		if (ctx.queryParam("amountLessThan") != null)
			greaterAmount = ctx.queryParam("amountLessThan");
		if (ctx.queryParam("amountGreaterThan") != null)
			lesserAmount = ctx.queryParam("amountGreaterThan");

		if (greaterAmount == "" && lesserAmount == "") {
			Client client = accountService.getAccounts(clientID);			 
			ctx.json(client.getAccounts());
			ctx.status(200);			
		} else {

			ArrayList<Account> accounts = accountService.getAccountByBalance(clientID, greaterAmount, lesserAmount);

			ctx.json(accounts);
			ctx.status(200);
		}
	};

	private Handler getAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");

		Client client = accountService.getAccount(clientID, accountID);
		Account account = client.getAccounts().get(0); 
		
		StringBuilder html = new StringBuilder(); 
		html.append("<h1>Get Account For " + client.getFirstName() + " " + client.getLastName() + "</h1>"); 
		html.append("<p>Account ID: " + account.getAccountId() + "</p>"); 
		html.append("<p>Account Type: " + account.getAccountType() + "</p>"); 
		html.append("<p>Account Name: " + account.getAccountName() +"</p>");
		html.append("<p>Account Balance: " + account.getBalance() + "</p>"); 

		ctx.html(html.toString()); 
		ctx.status(200);

	};

	private Handler updateAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");

		PostAccountDTO accountToBeUpdated = ctx.bodyAsClass(PostAccountDTO.class);

		Client client = accountService.updateAccount(clientID, accountID, accountToBeUpdated);
		
		ctx.status(204);
	};

	private Handler deleteAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");

		Client client = accountService.deleteAccount(clientID, accountID);
		Account account = client.getAccounts().get(0); 
		
		StringBuilder html = new StringBuilder(); 
		html.append("<h1> Deleted Account From " + client.getFirstName() + " " + client.getLastName() + "</h1>"); 
		html.append("<p>Account ID: " + account.getAccountId() + "</p>"); 
		html.append("<p>Account Type: " + account.getAccountType() + "</p>"); 
		html.append("<p>Account Name: " + account.getAccountName() +"</p>");
		html.append("<p>Account Balance: " + account.getBalance() + "</p>"); 
		

	    ctx.html(html.toString());
		ctx.status(200);

	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/clients/:clientid/accounts", addAccount); 
		app.get("/clients/:clientid/accounts", getAccounts);
		app.get("/clients/:clientid/accounts/:accountid", getAccount);
		app.put("/clients/:clientid/accounts/:accountid", updateAccount);
		app.delete("/clients/:clientid/accounts/:accountid", deleteAccount); 
	}

}
