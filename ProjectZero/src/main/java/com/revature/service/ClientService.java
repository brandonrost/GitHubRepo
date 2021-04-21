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
import com.revature.exceptions.ClientNotDeletedException;
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
		logger.info("Performing business logic now inside of the " + this.getClass());
		ArrayList<Client> clientArray = clientRepository.getClients();
		logger.info("SQL Query Success! Now back inside Service layer performing business logic.");
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			
			if (clientArray == null) {
				throw new ClientListNullException("There are no Clients in the 'Client Table' at this moment.");
			}
			
		}catch(SQLException e1) {
			throw new DatabaseException("Could not connect with the database." + e1.getMessage()); 
		}	
		
		return clientArray;
	}

	public Client getClientById(String stringID) throws BadParameterException, ClientNotFoundException, DatabaseException {
		logger.info("Performing business logic now inside of the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int id = Integer.parseInt(stringID);
			
			Client client = clientRepository.getClientById(id);
			logger.info("SQL Query Success! Now back inside Service layer performing business logic.");
			
			if(client == null ) {
				throw new ClientNotFoundException("Client with ID of '" + stringID + "' was not found.");
			}else {
				return client;
			}

		} catch (SQLException e3) {
			throw new DatabaseException("Could not connect to the database." + e3.getMessage()); 
		} catch (NumberFormatException e1) {
			throw new BadParameterException("Client ID must be of type (int). User provided '" + stringID + "'.");
		}

	}

	public Client addClient(PostClientDTO clientDTO) throws EmptyClientNameException, DatabaseException, ClientNotAddedException, BadParameterException {
		logger.info("Performing business logic now inside of the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(false);

			if (clientDTO.getFirstName().trim().strip() == "" || clientDTO.getLastName().trim().strip() == "") {
				throw new EmptyClientNameException("Client first/last name can not be null. Please try again!");
			}
			

			Client client = clientRepository.addClient(clientDTO);
			connection.commit();
			logger.info("SQL Query Success! Now back inside Service layer performing business logic.");
			
			return client;

		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. " + "Exception message: " + e.getMessage());
		} 
	}

	public Client updateClient(String clientID, PutClientDTO clientDTO) throws BadParameterException, SQLException, DatabaseException, EmptyClientNameException, ClientNotFoundException {
		logger.info("Performing business logic now inside of the " + this.getClass());
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
			connection.commit();
			logger.info("SQL Query Success! Now back inside Service layer performing business logic.");
			
			if(client == null) {
				throw new ClientNotFoundException("Client with ID of '" + clientID + "' was not found."); 
			}
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the Database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID must be of type (int). User provided '" + clientID + "'."); 
		}
	}

	public Client deleteClient(String clientID) throws DatabaseException, BadParameterException, ClientNotFoundException, ClientNotDeletedException {
		logger.info("Performing business logic now inside of the " + this.getClass());
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepository.setConnection(connection);
			connection.setAutoCommit(true);
			
			int id = Integer.parseInt(clientID);
			
			Client client = clientRepository.deleteClient(clientID);
			connection.commit();
			logger.info("SQL Query Success! Now back inside Service layer performing business logic.");
			
			return client; 
			
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the Database. Exception Message: " + e.getMessage()); 
		} catch (NumberFormatException e2) {
			throw new BadParameterException("Client ID must be of type (int). User provided '" + clientID + "'."); 
		}
	}

}
