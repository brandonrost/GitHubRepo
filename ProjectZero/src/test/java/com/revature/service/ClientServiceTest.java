package com.revature.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*; 

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.ClientRepository;
import com.revature.dto.PostClientDTO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientListNullException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.models.Client;

public class ClientServiceTest {
	
	private static ClientRepository mockClientRepository;
	public ClientService clientService;
	public static ArrayList<Client> clientArray;

	@BeforeClass
	public static void setUp() throws DatabaseException {
		mockClientRepository = mock(ClientRepository.class);

		when(mockClientRepository.getClientById(eq(1))).
		thenReturn(new Client("1", "Tom", "Hanks")); 
		
		when(mockClientRepository.getClientById(eq(2))).
		thenReturn(new Client("2","Ben","Aflec"));
		
		clientArray = new ArrayList<Client>();
		clientArray.add(new Client("1", "Tom", "Hanks")); 
		clientArray.add(new Client("2","Ben","Aflec"));
		
		when(mockClientRepository.getClients())
		.thenReturn(clientArray);
		
	}
	
	@Before
	public void beforeEachTest() {
		clientService = new ClientService(mockClientRepository);
	}

	@Test
	public void test_getClientByID_validIDof1() throws BadParameterException, ClientNotFoundException {
		Client actual = clientService.getClientById("1");
		
		Client expected = new Client("1", "Tom", "Hanks"); 
		
		assertEquals(expected, actual); 
	}
	
	@Test(expected=ClientNotFoundException.class)
	public void test_getClientByID_idNotFoundof3()  throws BadParameterException, ClientNotFoundException{
		Client actual = clientService.getClientById("3"); 
	}
	
	@Test(expected=BadParameterException.class)
	public void test_getClientByID_idNotFormattedCorrectly() throws BadParameterException, ClientNotFoundException{
		Client actual = clientService.getClientById("abc"); 
	}
	
	@Test
	public void test_getExceptionMessage_getClientByID_idNotFormattedCorrectly()throws BadParameterException, ClientNotFoundException{
		try {
			clientService.getClientById("abc");
			
			fail("Exception did not occur");
		} catch (BadParameterException e) {
			assertEquals("Client ID must be of type (int). User provided 'abc'.", e.getMessage());
		}
	}
	
	@Test
	public void test_getClients_ClientListHasItems() throws ClientListNullException, DatabaseException { 
		ArrayList<Client> actual = clientService.getClients(); 
		ArrayList<Client> expected = clientArray;
		assertEquals(expected, actual); 
	}
	
	@Test(expected=ClientListNullException.class)
	public void test_getClients_ClientListNull() throws ClientListNullException, DatabaseException{
		when(mockClientRepository.getClients()).thenReturn(null); 
		ArrayList<Client> actual = clientService.getClients(); 
	}

}
