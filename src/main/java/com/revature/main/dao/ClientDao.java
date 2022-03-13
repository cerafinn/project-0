package com.revature.main.dao;

import com.revature.main.model.Account;
import com.revature.main.model.Client;
import com.revature.main.utility.ConnectionUtility;

import java.sql.*;
import java.util.Collection;

//add
public class ClientDao {
  public Client addClient(Client client) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "INSERT INTO clients (first_name, last_name, age) VALUES (?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, client.getFirstName());
      ps.setString(2, client.getLastName());
      ps.setInt(3, client.getAge());
      // do we need to set accounts or will leave associated with accountdao?
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
  }
  //get all

  //update
  //delete
}
