package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {
  ClientDao mockedObj = mock(ClientDao.class);

  @Test
  public void testgetAllClients() throws SQLException {
    List<Client> testClients = new ArrayList<>();
    testClients.add(new Client(1, "Bill", "Smith", 20));
    testClients.add(new Client(2, "Test", "Test", 15));
    testClients.add(new Client(3, "John", "Doe", 30));

    when(mockedObj.getAllClients()).thenReturn(testClients);

    ClientService clientService = new ClientService(mockedObj);
    List<Client> actual = clientService.getAllClients();
    List<Client> expected = new ArrayList<>(testClients);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testGetClientById_confirm() throws SQLException, ClientNotFoundException {
    Client testClient = new Client(22, "Alex", "Harper", 32);
    when(mockedObj.getClientById(eq(22))).thenReturn(testClient);

    ClientService clientService = new ClientService(mockedObj);
    Client actual = clientService.getClientById("22");
    Client expected = new Client(22, "Alex", "Harper", 32);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testGetClientById_doesNotExist() throws SQLException, ClientNotFoundException {
    ClientService clientService = new ClientService(mockedObj);

    Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.getClientById("19"));
  }

  @Test
  public void testGetClientById_invalidEntry() throws SQLException, ClientNotFoundException {
    ClientDao mockedObj = mock(ClientDao.class);
    ClientService clientService = new ClientService(mockedObj);

    try{
      clientService.getClientById("test");
      fail();
    } catch (IllegalArgumentException e) {
      String expectedMessage = "id provided must be a valid int";
      String actualMessage = e.getMessage();
      Assertions.assertEquals(expectedMessage, actualMessage);
    }
  }

  @Test
  public void testGetClientById_sqlException() throws SQLException {
    ClientDao mockedObj = mock(ClientDao.class);
    when(mockedObj.getClientById(anyInt())).thenThrow(SQLException.class);
    ClientService clientService = new ClientService(mockedObj);
    Assertions.assertThrows(SQLException.class, () -> { clientService.getClientById("55");});
  }

  @Test
  public void testAddClient_confirm() throws SQLException {
    when(mockedObj.addClient(eq(new Client(0, "John", "Doe", 34))))
        .thenReturn(new Client(1, "John", "Doe", 34));

    ClientService clientService = new ClientService(mockedObj);

    Client actual = clientService.addClient( new Client(0, "John", "Doe", 34));
    Client expected = new Client(1, "John", "Doe", 34);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testAddClient_trim() throws SQLException {
    when(mockedObj.addClient(eq(new Client(0, "John", "Doe", 34))))
        .thenReturn(new Client(1, "John", "Doe", 34));

    ClientService clientService = new ClientService(mockedObj);

    Client actual = clientService.addClient( new Client(0, "    John    ", "   Doe   ", 34));
    Client expected = new Client(1, "John", "Doe", 34);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testAddClient_invalidFirstName() throws SQLException {
    ClientService clientService = new ClientService(mockedObj);

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      clientService.addClient(new Client(1, "J0hn", "Doe", 34));
    });
  }

  @Test
  public void testAddClient_invalidLastName() throws SQLException {
    ClientService clientService = new ClientService(mockedObj);

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      clientService.addClient(new Client(1, "John", "Doe789", 34));
    });
  }

  @Test
  public void testAddClient_invalidAge() {
    ClientService clientService = new ClientService(mockedObj);
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      clientService.addClient(new Client(0, "John", "Doe", -5));
    });
  }

//  @Test
//  public void testUpdateClient() throws SQLException, ClientNotFoundException {
//  }

//  @Test
//  public void testDeleteClient() throws SQLException, ClientNotFoundException {
//    Client testClient = new Client(22, "Alex", "Harper", 32);
//    when(mockedObj.deleteClient(eq(22))).thenReturn(true);
//    Assertions.assertEquals(expected, actual);
//  }
}
