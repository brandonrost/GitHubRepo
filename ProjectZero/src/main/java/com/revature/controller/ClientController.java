package com.revature.controller;

import java.util.ArrayList;

import com.revature.models.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;

public class ClientController implements Controller {
	
	private ClientService clientService; 
	
	public ClientController() {
		this.clientService = new ClientService(); 
	}
	
	private Handler getClients = ctx ->{
		ArrayList<Client> clientArray = clientService.getClients(); 
		ctx.json(clientArray); 
		ctx.status(200); 
	};
	
	private Handler getClientbyId = ctx ->{
		String id = ctx.pathParam("id"); 
		
		Client client= clientService.getClientById(id); 
		
		ctx.json(client); 
		ctx.status(200); 
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/clients", getClients);
		app.get("/clients/:id", getClientbyId);
	}
	
}
