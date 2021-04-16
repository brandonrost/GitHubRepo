package com.revature.dao;

import java.util.ArrayList;

import com.revature.dto.PostClientDTO;
import com.revature.dto.PutClientDTO;
import com.revature.models.Client;

public class ClientRepository {
	// Perform database operations such as retrieving a client, updating a client,
	// deleting a client.
	// Need to fill in with SQL queries eventually

	public Client getClientById(int id) {
		if (id == 1) {
			return new Client("1", "Tom", "Hanks");
		} else
			return null;
	}

	public ArrayList<Client> getClients() {
		ArrayList<Client> clientArray = new ArrayList<Client>();
		for (int i = 0; i < 2; i++) {
			Client client = new Client(String.valueOf(i+1), "Tom", "Hanks");
			clientArray.add(client);
		}
		if(clientArray == null)return null; 
		
		return clientArray;
	}
	
	public Client addClient(PostClientDTO clientDTO) {
		Client clientToBeAdded = new Client("Bill", "Burr");  
		return clientToBeAdded; 
	}

	public Client updateClient(Client clientToBeUpdated) {
		//UPDATE table WHERE client.id = clientDTO
		//return the clients new attributes (what was updated)
		
		Client updatedClient = new Client(clientToBeUpdated.getId(), clientToBeUpdated.getFirstName(), clientToBeUpdated.getLastName()); 
		return updatedClient;
	}

	public Client deleteClient(String clientID) {
		//DELETE ... FROM client WHERE client.id = clientID
		Client deletedClient = new Client(clientID, "Tom", "Cruise"); 
		return deletedClient;
	}
	

}
