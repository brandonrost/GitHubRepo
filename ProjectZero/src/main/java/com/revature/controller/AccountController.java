package com.revature.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.AddClientToAccountDTO;
import com.revature.dto.PostAccountDTO;
import com.revature.exceptions.NoAccountsFoundException;
import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller {

	private AccountService accountService;
	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	public AccountController() {
		this.accountService = new AccountService();
	}

	private Handler addAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");

		PostAccountDTO accountDTO = ctx.bodyAsClass(PostAccountDTO.class);

		Client client = this.accountService.addAccount(clientID, accountDTO);
		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<h1>Added Account</h1>\n");
		htmlString.append("<h3>Added Following Account To The Account Table:</h3>");
		htmlString.append("<hr><p><h3>ClientName: " + client.getFirstName() + " " + client.getLastName() + "</h3>");

		Account client_account = client.getAccounts().get(client.getAccounts().size() - 1);
		htmlString.append("<b>Account ID:</b> " + client_account.getAccountId() + "<br>" + "<b>Account Type:</b> "
				+ client_account.getAccountType() + "<br>" + "<b>Account Name:</b> " + client_account.getAccountName()
				+ "<br>" + "<b>Account Balance:</b> " + client_account.getBalance() + "</p>");
		ctx.html(htmlString.toString());
		ctx.status(201);
		logger.info("Endpoint mapped successfully!");
	};

	private Handler getAccounts = ctx -> {
		String clientID = ctx.pathParam("clientid");

		String greaterAmount = "";
		String lesserAmount = "";

		StringBuilder html = new StringBuilder();
		html.append("<h1>Get Accounts");

		if (ctx.queryParam("amountLessThan") != null) {
			greaterAmount = ctx.queryParam("amountLessThan");
			html.append(
					" By Balance</h1><p><h3>Showing Accounts That Are Less Than " + ctx.queryParam("amountLessThan"));
		}
		if (ctx.queryParam("amountGreaterThan") != null) {
			lesserAmount = ctx.queryParam("amountGreaterThan");
			if (ctx.queryParam("amountLessThan") == null) {
				html.append(" By Balance</h1><p><h3>Showing Accounts That Are Greater Than "
						+ ctx.queryParam("amountGreaterThan") + "</h3></p>");
			} else {
				html.append(" And Greater Than " + ctx.queryParam("amountGreaterThan") + "</h3></p>");
			}
		}else {
			html.append("</h3>");
		}
		if (greaterAmount == "" && lesserAmount == "") {
			Client client = accountService.getAccounts(clientID);
			ArrayList<Account> client_accounts = client.getAccounts(); 
			html.append("</h1>");
			html.append("<h3>Showing All Accounts For Client with ID of '" + clientID + "':</h3><hr>");
			if (client.getAccounts().size() > 0) {
				html.append("<table border = '1'>" + "<tr>" + "<th>AccountID</th>" + "<th>AccountType</th>"
						+ "<th>AccountName</th>" + "<th>AccountBalance</th>" + "</tr>");
				for (Account account : client_accounts) {
					html.append("<tr><td>" + account.getAccountId() + "</td>");
					html.append("<td>" + account.getAccountType() + "</td>");
					html.append("<td>" + account.getAccountName() + "</td>");
					html.append("<td>" + account.getBalance() + "</td></tr>");
				}
				html.append("</table>");
			}
			ctx.html(html.toString());
			ctx.status(200);
			logger.info("Endpoint to " + ctx.contextPath() + " mapped successfully!");

		} else {
			Client client = accountService.getAccountByBalance(clientID, greaterAmount, lesserAmount);
			ArrayList<Account> client_accounts = client.getAccounts();
			html.append("<h3>Client Name: " + client.getFirstName() + " " + client.getLastName() + "</h3><hr>");
			if (client_accounts.size() > 0) {
				html.append("<table border = '1'>" + "<tr>" + "<th>AccountID</th>" + "<th>AccountType</th>"
						+ "<th>AccountName</th>" + "<th>AccountBalance</th>" + "</tr>");
				for (Account account : client_accounts) {
					html.append("<tr><td>" + account.getAccountId() + "</td>");
					html.append("<td>" + account.getAccountType() + "</td>");
					html.append("<td>" + account.getAccountName() + "</td>");
					html.append("<td>" + account.getBalance() + "</td></tr>");
				}
				html.append("</table>");
			}
			ctx.html(html.toString());
			ctx.status(200);
			logger.info("Endpoint to " + ctx.contextPath() + " mapped successfully!");
		}
	};

	private Handler getAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");

		Client client = accountService.getAccount(clientID, accountID);
		Account account = client.getAccounts().get(0);

		StringBuilder html = new StringBuilder();
		html.append("<h1>Get Account For " + client.getFirstName() + " " + client.getLastName() + "</h1><hr>");
		html.append("<p><b>Account ID:</b> " + account.getAccountId() + "<br>");
		html.append("<b>Account Type:</b> " + account.getAccountType() + "<br>");
		html.append("<b>Account Name:</b> " + account.getAccountName() + "<br>");
		html.append("<b>Account Balance:</b> " + account.getBalance() + "</p>");

		ctx.html(html.toString());
		ctx.status(200);
		logger.info("Endpoint mapped successfully!");

	};

	private Handler updateAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");

		PostAccountDTO accountToBeUpdated = ctx.bodyAsClass(PostAccountDTO.class);

		Client client = accountService.updateAccount(clientID, accountID, accountToBeUpdated);
		Account updatedAccount = new Account(); 
		for(Account account:client.getAccounts()) {
			if(account.getAccountId().equals(accountID)) {
				updatedAccount = account; 
			}
		}
		if(updatedAccount.getAccountId()==null) {
			throw new NoAccountsFoundException(); 
		} else {
			StringBuilder html = new StringBuilder();
			html.append("<h1>Update Account For " + client.getFirstName() + " " + client.getLastName() + "</h1><hr>");
			html.append("<p><b>Account ID:</b> " + updatedAccount.getAccountId() + "<br>");
			html.append("<b>Account Type:</b> " + updatedAccount.getAccountType() + "<br>");
			html.append("<b>Account Name:</b> " + updatedAccount.getAccountName() + "<br>");
			html.append("<b>Account Balance:</b> " + updatedAccount.getBalance() + "</p>");

			ctx.html(html.toString());
			ctx.status(200);
			logger.info("Endpoint mapped successfully!");
		}		
	};

	private Handler deleteAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");

		Client client = accountService.deleteAccount(clientID, accountID);
		Account account = client.getAccounts().get(0);

		StringBuilder html = new StringBuilder();
		html.append("<h1> Deleted Account From " + client.getFirstName() + " " + client.getLastName() + "</h1><hr>");
		html.append("<p><b>Account ID:</b> " + account.getAccountId() + "<br>");
		html.append("<b>Account Type:</b> " + account.getAccountType() + "<br>");
		html.append("<b>Account Name:</b> " + account.getAccountName() + "<br>");
		html.append("<b>Account Balance:</b> " + account.getBalance() + "</p>");

		ctx.html(html.toString());
		ctx.status(200);
		logger.info("Endpoint mapped successfully!");

	};

	private Handler addClientToAccount = ctx -> {
		String clientID = ctx.pathParam("clientid");
		String accountID = ctx.pathParam("accountid");
		AddClientToAccountDTO clientToBeAdded = ctx.bodyAsClass(AddClientToAccountDTO.class);

		Client client = accountService.addClientToAccount(clientID, accountID, clientToBeAdded);

		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<h1>Added Client To Account</h1>");
		htmlString.append("<p><h3>ClientName: " + client.getFirstName() + " " + client.getLastName() + "</h3></p><hr>");

		Account client_account = client.getAccounts().get(client.getAccounts().size() - 1);
		htmlString.append("<p><b>Account Type:</b> " + client_account.getAccountType() + "<br>"
				+ "<b>Account Name:</b> " + client_account.getAccountName() + "<br>" + "<b>Account Balance:</b> "
				+ client_account.getBalance() + "</p>");

		ctx.html(htmlString.toString());
		ctx.status(201);
		logger.info("Endpoint mapped successfully!");
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/clients/:clientid/accounts", addAccount);
		app.post("/clients/:clientid/accounts/:accountid", addClientToAccount);
		app.get("/clients/:clientid/accounts", getAccounts);
		app.get("/clients/:clientid/accounts/:accountid", getAccount);
		app.put("/clients/:clientid/accounts/:accountid", updateAccount);
		app.delete("/clients/:clientid/accounts/:accountid", deleteAccount);
	}

}
