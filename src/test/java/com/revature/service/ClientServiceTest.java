package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {
  @Test
  public void testgetAllClients() throws SQLException {
    ClientDao mockedObj = mock(ClientDao.class);

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
}
