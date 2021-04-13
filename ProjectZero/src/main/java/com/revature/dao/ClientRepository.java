package com.revature.dao;

import java.util.ArrayList;

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
	
	

}
