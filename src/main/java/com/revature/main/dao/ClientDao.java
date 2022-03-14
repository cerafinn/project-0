package com.revature.main.dao;

import com.revature.main.model.Account;
import com.revature.main.model.Client;
import com.revature.main.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//add
public class ClientDao {
  public Client addClient(Client client) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "INSERT INTO clients (first_name, last_name, age) VALUES (?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, client.getFirstName());
      ps.setString(2, client.getLastName());
      ps.setInt(3, client.getAge());
      // do we need to set accounts or will leave associated with account endpoints?
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int generatedId = rs.getInt(1);

      return new Client(generatedId, client.getFirstName(), client.getLastName(), client.getAge(), client.getAccounts());
    }
  }

  //get one
  public Client getClientId(int id) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM clients WHERE id = ?";
      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        int age = rs.getInt("age");
        // how to pull accounts, need to create get all for id client

        return new Client(id, firstName, lastName, age);
      }
    }
    return null;
  }

  //get all
  public List<Client> getAllClients() throws SQLException {
    List<Client> clients = new ArrayList<>();

    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM clients";
      PreparedStatement ps = connection.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        int age = rs.getInt("age");
        //need return for accounts

        clients.add(new Client(id, firstName, lastName, age));
      }
    }
    return clients;
  }

  //update
  public Client updateClient(Client client) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "UPDATE students " + "SET first_name = ?, " + "last_name = ?, " +  "age = ? " +
          //account?
          "WHERE id = ?";

      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setString(1, client.getFirstName());
      ps.setString(2, client.getLastName());
      ps.setInt(3, client.getAge());
      ps.setInt(4, client.getId());

      ps.executeUpdate();
    }
    return client;
  }

  //delete
  public boolean deleteClient(int id) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "DELETE FROM clients WHERE id = ?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      int recordsDeleted = ps.executeUpdate();

      if (recordsDeleted == 1) {
        return true;
      }
    }
    return false;
  }
}
