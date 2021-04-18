package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.dto.PostClientDTO;
import com.revature.dto.PutClientDTO;
import com.revature.exceptions.DatabaseException;
import com.revature.models.Client;
import com.revature.util.ConnectionUtil;

public class ClientRepository {
	// Perform database operations such as retrieving a client, updating a client,
	// deleting a client.
	// Need to fill in with SQL queries eventually
	private Connection connection;

	public ClientRepository() {
		super();
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Client getClientById(int id) throws DatabaseException {
		
		try {
			String sql = "SELECT c.id, c.client_first_name, c.client_last_name, a.account_type, a.account_name, a.account_balance\r\n"
					+ "FROM client AS c\r\n"
					+ "LEFT JOIN client_account AS ca ON c.id = ca.client_id \r\n"
					+ "LEFT JOIN account AS a ON a.id = ca.account_id\r\n"
					+ "WHERE client_id = ?;"; 

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, id);

			int recordsAdded = pstmt.executeUpdate();

			if (recordsAdded != 1) {
				throw new DatabaseException("Couldn't add Client to the Database.");
			}

			ResultSet rs = pstmt.getGeneratedKeys();
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
			}
			
			
		} catch (SQLException e) {
			throw new DatabaseException(
					"Something went wrong with the database. " + "Exception message: " + e.getMessage());
		}
		return new Client(); 
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

	public Client updateClient(Client clientToBeUpdated) {
		// UPDATE table WHERE client.id = clientDTO
		// return the clients new attributes (what was updated)

		Client updatedClient = new Client(clientToBeUpdated.getId(), clientToBeUpdated.getFirstName(),
				clientToBeUpdated.getLastName());
		return updatedClient;
	}

	public Client deleteClient(String clientID) {
		// DELETE ... FROM client WHERE client.id = clientID
		Client deletedClient = new Client(clientID, "Tom", "Cruise");
		return deletedClient;
	}

}
