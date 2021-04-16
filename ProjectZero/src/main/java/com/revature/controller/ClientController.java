package com.revature.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.PostClientDTO;
import com.revature.dto.PutClientDTO;
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

		ctx.html("<h1>AddClient<h1>");
		ctx.json(insertedClient);
		// ctx.result("Client Added Successfully!\nID: " + insertedClient.getId() +
		// "\nFirst Name: " + insertedClient.getFirstName() +"\nLastName: " +
		// insertedClient.getLastName());
	};

	private Handler getClients = ctx -> {
		ArrayList<Client> clientArray = clientService.getClients();

		ctx.json(clientArray);
		ctx.status(200);
	};

	private Handler getClientbyId = ctx -> {
		String clientID = ctx.pathParam("clientid");

		Client client = clientService.getClientById(clientID);

		ctx.json(client);
		ctx.status(200);
	};

	private Handler updateClient = ctx -> {
		String clientID = ctx.pathParam("clientid");
		
		PutClientDTO clientDTO = ctx.bodyAsClass(PutClientDTO.class); 
		
		Client clientToBeUpdated = clientService.updateClient(clientID, clientDTO);
		
		ctx.json(clientToBeUpdated);
		ctx.status(200); 
	};
	
	private Handler deleteClient = ctx -> {
		String clientID = ctx.pathParam("clientid"); 
		
		Client client = clientService.deleteClient(clientID); 
		
		ctx.json(client); 
		ctx.status(200); 
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
