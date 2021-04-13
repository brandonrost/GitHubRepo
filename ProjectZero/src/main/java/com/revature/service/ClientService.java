package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.ClientRepository;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientListNullException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.models.Client;

public class ClientService {
	
	private ClientRepository clientRepository; 
	
	public ClientService() {
		this.clientRepository = new ClientRepository();
	}
	
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository; 
	}
	
	public ArrayList<Client> getClients() throws ClientListNullException{
		ArrayList<Client> clientArray = clientRepository.getClients(); 
		if(clientArray == null) {
			throw new ClientListNullException("There are no Clients in the 'Client Table' at this moment."); 
		}
		return clientArray; 
	}
	
	public Client getClientById(String stringID) throws BadParameterException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(stringID); 
			Client client = clientRepository.getClientById(id);
			if(client == null) {
				throw new ClientNotFoundException("Client with [id] of " + id + "not found."); 
			}
			return client;
		
		}catch(NumberFormatException e) {
			throw new BadParameterException("Client ID must be of type (int). User provided '"+ stringID +"'."); 
		}catch(ClientNotFoundException e) {
			throw new ClientNotFoundException("Client ID entered could not be found. User provided '"+ stringID + "'.");
		}
					
	}

}
