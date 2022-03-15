package com.revature.service;


import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
  //create service layer for client
  private static Logger logger = LoggerFactory.getLogger(ClientService.class);

  private ClientDao clientDao;
  public ClientService() { this.clientDao = new ClientDao(); }
  public ClientService(ClientService mockDao) { this.clientDao = mockDao; }

  public List<Client> getAllClients() throws SQLException {
    return this.clientDao.getAllClients();
  }

  public Client getClientById(String id) throws SQLException, ClientNotFoundException {
    try {
      int clientId = Integer.parseInt(id);
      Client c = clientDao.getClientById(clientId);
      if (c == null) {
        throw new ClientNotFoundException("Client with id " + clientId + " not found");
      }
      return c;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("id provided must be a valid int");
    }
  }

  public Client addClient(Client c) throws SQLException {
    validateClientInfo(c);
    Client newClient = clientDao.addClient(c);
    return newClient;
  }

  public Client editClient(String id, Client c) throws SQLException {
    try {
      int clientId = Integer.parseInt(id);

      if(clientDao.getClientById(clientId) == null) {
        throw new ClientNotFoundException("Client with " + clientId + " does not exist.");
      }
      validateClientInfo(c);
      c.setId(clientId);
      Client updatedClient = clientDao.updateClient(c);
      return updatedClient;
    } catch(NumberFormatException e) {
      throw new IllegalArgumentException("id provided must be a valid int");
    }
  }

  private void validateClientInfo(Client c) {

  }
}
