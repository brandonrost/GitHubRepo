package com.revature.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ClientRepository;
import com.revature.dto.PostClientDTO;
import com.revature.dto.PutClientDTO;
import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;

public class ClientController implements Controller {

	private ClientService clientService;
	private Logger logger = LoggerFactory.getLogger(ClientController.class);

	public ClientController() {
		this.clientService = new ClientService();
	}

	private Handler addClient = ctx -> {
		PostClientDTO clientDTO = ctx.bodyAsClass(PostClientDTO.class);

		Client insertedClient = clientService.addClient(clientDTO);
		
		StringBuilder html = new StringBuilder(); 
		html.append("<h1>AddClient</h1>");
		html.append("<hr>");
		html.append("<h4>Inserted Following Client Into Client Table:</h4>"); 
		html.append("<p><b>Client ID:</b> " + insertedClient.getId() + "<br>");
		html.append("<b>Client First Name:</b> "+ insertedClient.getFirstName() + "<br>");
		html.append("<b> Client Last Name:</b> " + insertedClient.getLastName() + "</p>");
		
		ctx.html(html.toString());
		ctx.status(201);
		logger.info("Endpoint to " + ctx.contextPath() + " mapped successfully!");
	};

	private Handler getClients = ctx -> {
		ArrayList<Client> clientArray = clientService.getClients();
		
		StringBuilder html = new StringBuilder(); 
		html.append("<h1>GetAllClients</h1>");
		html.append("<h4>Retrieved Following Clients From Client Table:</h4>"); 
		for(Client client : clientArray) {
			html.append("<hr>");
			html.append("<p><b>Client ID:</b> " + client.getId() + "<br>");
			html.append("<b>Client First Name:</b> "+ client.getFirstName() + "<br>");
			html.append("<b> Client Last Name:</b> " + client.getLastName() + "</p>");
		}

		ctx.json(html.toString());
		ctx.status(200);
		logger.info("Endpoint to " + ctx.contextPath() + " mapped successfully!");
	};

	private Handler getClientbyId = ctx -> {
		String clientID = ctx.pathParam("clientid");

		Client client = clientService.getClientById(clientID);
		ArrayList<Account> client_accounts = client.getAccounts(); 
		
		StringBuilder html = new StringBuilder(); 
		html.append("<h1>GetClientByID</h1>");
		html.append("<hr>");
		html.append("<h4>Retrieved Following Client From Client Table:</h4>"); 
		html.append("<p><b>Client ID:</b> " + client.getId() + "<br>");
		html.append("<b>Client First Name:</b> "+ client.getFirstName() + "<br>");
		html.append("<b> Client Last Name:</b> " + client.getLastName() + "</p>");
		if(client_accounts.size()>0) {
			html.append("<hr>");
			html.append("<table border = '1'>"
					+ "<tr>"
					+ "<th>AccountID</th>"
					+ "<th>AccountType</th>"
					+ "<th>AccountName</th>"
					+ "<th>AccountBalance</th>"
					+ "</tr>");
			for(Account account : client_accounts) {	
				html.append("<tr><td>"+ account.getAccountId() + "</td>");
				html.append("<td>"+ account.getAccountType() + "</td>");
				html.append("<td>"+ account.getAccountName() + "</td>");
				html.append("<td>"+ account.getBalance() + "</td></tr>");
			}
			
			html.append("</table>"); 			
		}			
		ctx.html(html.toString());
		ctx.status(202); 
		logger.info("Endpoint to " + ctx.contextPath() + " mapped successfully!");
	};

	private Handler updateClient = ctx -> {
		String clientID = ctx.pathParam("clientid");
		
		PutClientDTO clientDTO = ctx.bodyAsClass(PutClientDTO.class); 
		
		Client oldClient = clientService.getClientById(clientID); 
		
		Client clientToBeUpdated = clientService.updateClient(clientID, clientDTO);
		
		StringBuilder html = new StringBuilder(); 
		html.append("<h1>UpdateClient</h1>");
		html.append("<hr>");
		html.append("<h3>Updated Following Client From This:</h3>"); 
		html.append("<p><b>Client ID:</b> " + oldClient.getId() + "<br>");
		html.append("<b>Client First Name:</b> "+ oldClient.getFirstName() + "<br>");
		html.append("<b> Client Last Name:</b> " + oldClient.getLastName() + "</p>");
		html.append("<hr>");
		html.append("<h3>To This:</h3>"); 
		html.append("<p><b>Client ID:</b> " + clientToBeUpdated.getId() + "<br>");
		html.append("<b>Client First Name:</b> "+ clientToBeUpdated.getFirstName() + "<br>");
		html.append("<b> Client Last Name:</b> " + clientToBeUpdated.getLastName() + "</p>");
		
		
		ctx.html(html.toString());
		ctx.status(200);
		logger.info("Endpoint to " + ctx.contextPath() + " mapped successfully!");
	};
	
	private Handler deleteClient = ctx -> {
		String clientID = ctx.pathParam("clientid"); 
		
		Client client = clientService.deleteClient(clientID); 
		StringBuilder html = new StringBuilder(); 
		html.append("<h1>DeleteClient</h1>");
		html.append("<hr>");
		html.append("<h3>Deleted Following Client From Client Table:</h3>"); 
		html.append("<p><b>Client ID:</b> " + client.getId() + "<br>");
		html.append("<b>Client First Name:</b> "+ client.getFirstName() + "<br>");
		html.append("<b> Client Last Name:</b> " + client.getLastName() + "</p>");
		
		ctx.html(html.toString()); 
		ctx.status(202); 
		logger.info("Endpoint to " + ctx.contextPath() + " mapped successfully!");
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/clients", addClient);// POST /clients: Creates a new client
		app.get("/clients", getClients);// Gets all clients
		app.get("/clients/:clientid", getClientbyId); // Gets Client by ID
		app.put("/clients/:clientid", updateClient); // PUT /clients/{id}: Update client with an id of X (if the client exists)
		app.delete("/clients/:clientid", deleteClient);// DELETE /clients/{id}: Delete client with an id of X (if the client exists)
	}

}
