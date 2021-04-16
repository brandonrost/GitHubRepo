package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.ClientRepository;
import com.revature.dto.PostClientDTO;
import com.revature.dto.PutClientDTO;
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

	public Client addClient(PostClientDTO clientDTO) {
		//The next line will be used when establishing connection to database		
		//Client client = clientRepository.addClient(clientDTO);
		
		//for now pass DTO client
		Client client = new Client(clientDTO.getFirstName(), clientDTO.getLastName()); 
		return client; 
	}

	public Client updateClient(String clientID, PutClientDTO clientDTO) {
		Client clientToBeUpdated = new Client(clientID, clientDTO.getFirstName(), clientDTO.getLastName()); 
		Client updatedClient = clientRepository.updateClient(clientToBeUpdated); 
		return updatedClient; 
	}

	public Client deleteClient(String clientID) {
		Client clientToBeDeleted = clientRepository.deleteClient(clientID); 
		return clientToBeDeleted;
	}

}
