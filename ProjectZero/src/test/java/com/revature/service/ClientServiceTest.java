package com.revature.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

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

public class ClientServiceTest {

	private static ClientRepository mockClientRepository;
	private ClientService clientService;
	public static ArrayList<Client> clientArray;
	private static Connection mockConnection;

	@BeforeClass
	public static void setUp()
			throws DatabaseException, ClientNotFoundException, ClientListNullException, ClientNotAddedException {
		mockClientRepository = mock(ClientRepository.class);
		mockConnection = mock(Connection.class);

		when(mockClientRepository.getClientById(eq(1))).thenReturn(new Client("1", "Tom", "Hanks"));

		when(mockClientRepository.getClientById(eq(2))).thenReturn(new Client("2", "Ben", "Aflec"));

		clientArray = new ArrayList<Client>();
		clientArray.add(new Client("1", "Tom", "Hanks"));
		clientArray.add(new Client("2", "Ben", "Aflec"));

		when(mockClientRepository.getClients()).thenReturn(clientArray);

		when(mockClientRepository.addClient(eq(new PostClientDTO("Tom", "Cruise"))))
				.thenReturn(new Client("10", "Tom", "Cruise"));

		when(mockClientRepository.updateClient(eq(new Client("5", "Billy", "Bonkers"))))
				.thenReturn(new Client("5", "Billy", "Bonkers"));

	}

	@Before
	public void beforeEachTest() {
		clientService = new ClientService(mockClientRepository);
	}

	@Test
	public void test_getClientByID_validIDof1()
			throws BadParameterException, ClientNotFoundException, DatabaseException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.getClientById("1");

			Client expected = new Client("1", "Tom", "Hanks");

			assertEquals(expected, actual);
		}
	}

	@Test(expected = ClientNotFoundException.class)
	public void test_getClientByID_idNotFoundof3()
			throws BadParameterException, ClientNotFoundException, DatabaseException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.getClientById("3");
		}
	}

	@Test(expected = BadParameterException.class)
	public void test_getClientByID_idNotFormattedCorrectly()
			throws BadParameterException, ClientNotFoundException, DatabaseException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.getClientById("abc");
		}
	}

	@Test
	public void test_getExceptionMessage_getClientByID_idNotFormattedCorrectly()
			throws BadParameterException, ClientNotFoundException, DatabaseException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			try {
				clientService.getClientById("abc");
				fail("Exception did not occur");

			} catch (BadParameterException e) {
				assertEquals("Client ID must be of type (int). User provided 'abc'.", e.getMessage());
			}
		}
	}

	@Test
	public void test_getClients_ClientListHasItems() throws ClientListNullException, DatabaseException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			ArrayList<Client> actual = clientService.getClients();
			ArrayList<Client> expected = clientArray;
			assertEquals(expected, actual);
		}
	}

	@Test(expected = ClientListNullException.class)
	public void test_getClients_ClientListNull() throws ClientListNullException, DatabaseException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			when(mockClientRepository.getClients()).thenReturn(null);
			ArrayList<Client> actual = clientService.getClients();
		}
	}

	@Test
	public void test_addClient_happyPath()
			throws EmptyClientNameException, DatabaseException, ClientNotAddedException, BadParameterException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.addClient(new PostClientDTO("Tom", "Cruise"));
			Client expected = new Client("10", "Tom", "Cruise");
			assertEquals(expected, actual);
		}
	}

	@Test(expected = EmptyClientNameException.class)
	public void test_addClient_blank_firstName()
			throws EmptyClientNameException, DatabaseException, ClientNotAddedException, BadParameterException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.addClient(new PostClientDTO("", "Cruise"));
		}
	}

	@Test(expected = EmptyClientNameException.class)
	public void test_addClient_blank_lastName()
			throws EmptyClientNameException, DatabaseException, ClientNotAddedException, BadParameterException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.addClient(new PostClientDTO("Tom", ""));
		}
	}

	@Test(expected = EmptyClientNameException.class)
	public void test_addClient_blank_firstName_withSpaces()
			throws EmptyClientNameException, DatabaseException, ClientNotAddedException, BadParameterException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.addClient(new PostClientDTO("      ", "Cruise"));
		}
	}

	@Test(expected = EmptyClientNameException.class)
	public void test_addClient_blank_lastName_withSpaces()
			throws EmptyClientNameException, DatabaseException, ClientNotAddedException, BadParameterException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.addClient(new PostClientDTO("Tom", "     "));
		}
	}

	@Test
	public void test_updateClient_happyPath()
			throws SQLException, BadParameterException, DatabaseException, EmptyClientNameException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.updateClient("5", new PutClientDTO("Billy", "Bonkers"));
			Client expected = new Client("5", "Billy", "Bonkers");
			
			assertEquals(expected, actual); 
		}
	}
	
	@Test
	public void test_updateClient_blankFirstName()
			throws SQLException, BadParameterException, DatabaseException, EmptyClientNameException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.updateClient("5", new PutClientDTO("", "Bonkers"));
			Client expected = new Client("5", "Billy", "Bonkers");
			
			fail("EmptyClientNameException was not thrown.");
			
		} catch(EmptyClientNameException e) {
			assertEquals(e.getMessage(), "Client name can not be null. Please fill in first and last name."); 
		}
	}
	
	@Test
	public void test_updateClient_blankLastName()
			throws SQLException, BadParameterException, DatabaseException, EmptyClientNameException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.updateClient("5", new PutClientDTO("Billy", ""));
			Client expected = new Client("5", "Billy", "Bonkers");
			
			fail("EmptyClientNameException was not thrown.");
			
		} catch(EmptyClientNameException e) {
			assertEquals(e.getMessage(), "Client name can not be null. Please fill in first and last name."); 
		}
	}
	
	@Test
	public void test_updateClient_blankFirstName_withSpaces()
			throws SQLException, BadParameterException, DatabaseException, EmptyClientNameException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.updateClient("5", new PutClientDTO("     ", "Bonkers"));
			Client expected = new Client("5", "Billy", "Bonkers");
			
			fail("EmptyClientNameException was not thrown.");
			
		} catch(EmptyClientNameException e) {
			assertEquals(e.getMessage(), "Client name can not be null. Please fill in first and last name."); 
		}
	}
	
	@Test
	public void test_updateClient_blankLastName_withSpaces()
			throws SQLException, BadParameterException, DatabaseException, EmptyClientNameException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.updateClient("5", new PutClientDTO("Billy", "      "));
			Client expected = new Client("5", "Billy", "Bonkers");
			
			fail("EmptyClientNameException was not thrown.");
			
		} catch(EmptyClientNameException e) {
			assertEquals(e.getMessage(), "Client name can not be null. Please fill in first and last name."); 
		}
	}
	
	@Test
	public void test_updateClient_clientDoesntExist()
			throws SQLException, BadParameterException, DatabaseException, EmptyClientNameException {
		try (MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);

			Client actual = clientService.updateClient("3", new PutClientDTO("Billy", "Bonkers"));
			Client expected = new Client("3", "Billy", "Bonkers");
			
			fail("EmptyClientNameException was not thrown.");
			
		} catch(EmptyClientNameException e) {
			assertEquals(e.getMessage(), "Client name can not be null. Please fill in first and last name."); 
		}
	}
}
