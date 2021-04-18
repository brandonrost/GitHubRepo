package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.PostClientDTO;
import com.revature.dto.PutClientDTO;
import com.revature.exceptions.DatabaseException;
import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.util.ConnectionUtil;

public class ClientRepository {
	// Perform database operations such as retrieving a client, updating a client,
	// deleting a client.
	// Need to fill in with SQL queries eventually
	private Connection connection;
	private Logger logger = LoggerFactory.getLogger(ClientRepository.class); 

	public ClientRepository() {
		super();
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Client getClientById(int id) throws DatabaseException {
		logger.info("Accessing the database inside of the " + this.getClass());		
		try {
			String sql = "SELECT c.id, c.client_first_name, c.client_last_name, a.id, a.account_type, a.account_name, a.account_balance "
					+ "FROM client AS c "
					+ "LEFT JOIN client_account AS ca ON c.id = ca.client_id "
					+ "LEFT JOIN account AS a ON a.id = ca.account_id "
					+ "WHERE c.id = ?;";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, id);

			boolean recordsAdded = pstmt.execute();
			logger.info("Executed SQL Statement: " + sql);
			
			if (recordsAdded == false) {
				throw new DatabaseException("Could not find Client with that ID. User entered " + id);
			}

			ResultSet rs = pstmt.getResultSet(); 
			Client client = new Client(); 
			while(rs.next()) {
				if(rs.isFirst()) {
					String client_id = String.valueOf(rs.getInt(1));
					String client_first_name = rs.getString(2);
					String client_last_name = rs.getString(3); 
					client.setFirstName(client_first_name);
					client.setLastName(client_last_name);
					client.setId(client_id);
				}
				String account_id = String.valueOf(rs.getInt(4));
				String account_type = rs.getString(5);
				String account_name = rs.getString(6); 
				int account_balance = rs.getInt(7);
				client.addAccount(new Account(account_id, account_type, account_name, account_balance));
			}
			
			return client; 
			
			
		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong with the database. " + "Exception message: " + e.getMessage());
		} catch (DatabaseException e2) {
			throw new DatabaseException("Could not find Client with that ID. User entered " + id); 
		}
	}

	public ArrayList<Client> getClients() throws DatabaseException {
		ArrayList<Client> clientArray = new ArrayList<Client>();

		try (Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM client;";

			Statement stmt = connection.createStatement();

			int recordsAdded = stmt.executeUpdate(sql);

			ResultSet rs = stmt.getResultSet();
			
			System.out.println(rs.getRow());
			
			if (rs.next()==false) {
				throw new DatabaseException("Could Not retrieve clients from database.");
			}
			rs.beforeFirst();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				clientArray.add(new Client(String.valueOf(id), firstName, lastName));
			}
			
		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong with the database. " + "Exception message: " + e.getMessage());
		}
		
		return clientArray;
	}

	public Client addClient(PostClientDTO clientDTO) throws DatabaseException {

		try {
			String sql = "INSERT INTO client (client_first_name, client_last_name) VALUES (?,?)";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, clientDTO.getFirstName());
			pstmt.setString(2, clientDTO.getLastName());

			int recordsAdded = pstmt.executeUpdate();

			if (recordsAdded != 1) {
				throw new DatabaseException("Couldn't add Client to the Database.");
			}

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				Client newClient = new Client(String.valueOf(id), clientDTO.getFirstName(), clientDTO.getLastName());
				return newClient;
			} else {
				throw new DatabaseException(
						"Client ID was not generated; therefore, Client was not successfully added.");
			}

		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong with the database. " + "Exception message: " + e.getMessage());
		}

	}

	public Client updateClient(Client clientToBeUpdated) throws DatabaseException {
		logger.info("Accessing the database through the " + this.getClass());
		try {
			String sql = "UPDATE client SET client_first_name = ?, client_last_name = ? WHERE client.id = ?";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, clientToBeUpdated.getFirstName());
			pstmt.setString(2, clientToBeUpdated.getLastName());
			pstmt.setInt(3, Integer.valueOf( clientToBeUpdated.getId()));
			
			int recordsAdded = pstmt.executeUpdate(); 
			
			if(recordsAdded == 0) {
				throw new DatabaseException("Could not update Client with new information."); 
			}
			
			logger.info("Executed SQL Statement: " + sql);
			
			return clientToBeUpdated;
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with the database query. Exception Message: " + e.getMessage()); 
		}
	}

	public Client deleteClient(String clientID) throws DatabaseException {
		logger.info("Accessing the database through the " + this.getClass());
		try {
			String sql = "SELECT * FROM client WHERE client.id = ?;";
			
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, Integer.valueOf(clientID));
			
			boolean recordsAdded = pstmt.execute();
			
			System.out.println(recordsAdded); 
			
			if(recordsAdded == false) {
				throw new DatabaseException("Could not delete Client from the 'client' table."); 
			}
			
			logger.info("Executed SQL Statement: " + sql);
			
			ResultSet rs = pstmt.getResultSet(); 
			
			Client client = new Client(); 			
			if(rs.next()) {
				client.setId(clientID);
				client.setFirstName(rs.getString(2));
				client.setLastName(rs.getString(3));
				System.out.println(client.toString());
			}else {
				throw new DatabaseException("Could not delete Client from the 'client' table."); 
			}
						
			String sql2 = "DELETE FROM client WHERE client.id = ?;";

			PreparedStatement pstmt2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			pstmt2.setInt(1, Integer.valueOf(clientID));
			
			int recordsAdded2 = pstmt2.executeUpdate(); 
			
			if(recordsAdded2 == 0) {
				throw new DatabaseException("Could not delete Client from the 'client' table."); 
			}
			
			logger.info("Executed SQL Statement: " + sql);
			
			return client;
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with the database query. Exception Message: " + e.getMessage()); 
		}
	}

}
