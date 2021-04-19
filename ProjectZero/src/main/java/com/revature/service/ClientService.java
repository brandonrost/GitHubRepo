package com.revature.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ClientRepository;
import com.revature.dto.PostClientDTO;
import com.revature.dto.PutClientDTO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientListNullException;
import com.revature.exceptions.ClientNotAddedException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.EmptyClientNameException;
import com.revature.models.Client;
import com.revature.util.ConnectionUtil;

public class ClientService {

	private Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ClientRepository clientRepository;

	public ClientService() {
		this.clientRepository = new ClientRepository();
	}

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public ArrayList<Client> getClients() throws ClientListNullException, DatabaseException {
		ArrayList<Client> clientArray = clientRepository.getClients();
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			
			if (clientArray == null) {
				throw new ClientListNullException("There are no Clients in the 'Client Table' at this moment.");
			}
			
		}catch(SQLException e1) {
			throw new DatabaseException("Could not connect with the database." + e1.getMessage()); 
		}catch(ClientListNullException e2) {
			throw new ClientListNullException("\"There are no Clients in the 'Client Table' at this moment."); 
		}
		
		
		
		return clientArray;
	}

	public Client getClientById(String stringID) throws BadParameterException, ClientNotFoundException, DatabaseException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int id = Integer.parseInt(stringID);
			
			Client client = clientRepository.getClientById(id);
			
			if (client == null) {
				throw new ClientNotFoundException("Client with [id] of " + id + "not found.");
			}
			return client;

		} catch (NumberFormatException e1) {
			throw new BadParameterException("Client ID must be of type (int). User provided '" + stringID + "'.");
		} catch (ClientNotFoundException e2) {
			throw new ClientNotFoundException("Client ID entered could not be found. User provided '" + stringID + "'.");
		} catch (SQLException e3) {
			throw new DatabaseException("Could not connect to the database." + e3.getMessage()); 
		}

	}

	public Client addClient(PostClientDTO clientDTO) throws EmptyClientNameException, DatabaseException, ClientNotAddedException, BadParameterException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(false);

			if (clientDTO.getFirstName().trim().strip() == "" || clientDTO.getLastName().trim().strip() == "") {
				throw new EmptyClientNameException("Client first/last name can not be null. Please try again!");
			}
			

			Client client = clientRepository.addClient(clientDTO);
			connection.commit();
			return client;

		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong when trying to get a connection. " + "Exception message: " + e.getMessage());
		} catch (EmptyClientNameException e2) {
			throw new BadParameterException("Client first/last name can not be null. Please try again!"); 
		}
	}

	public Client updateClient(String clientID, PutClientDTO clientDTO) throws BadParameterException, SQLException, DatabaseException, EmptyClientNameException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int id = Integer.parseInt(clientID);
			
			if(clientDTO.getFirstName().trim().strip() == "" || clientDTO.getLastName().trim().strip() == "") {
				throw new EmptyClientNameException("Client name can not be null. Please fill in first and last name."); 
			}
			Client updatedClient = new Client(clientID, clientDTO.getFirstName(), clientDTO.getLastName());
			Client client = clientRepository.updateClient(updatedClient);
			
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the Database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID must be of type (int). User provided '" + clientID + "'."); 
		}
	}

	public Client deleteClient(String clientID) throws DatabaseException, BadParameterException, ClientNotFoundException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int id = Integer.parseInt(clientID);
			
			Client client = clientRepository.deleteClient(clientID);
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the Database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID must be of type (int). User provided '" + clientID + "'."); 
		}
	}

}
