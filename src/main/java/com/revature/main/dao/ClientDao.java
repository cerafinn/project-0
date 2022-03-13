package com.revature.main.dao;

import com.revature.main.model.Client;
import com.revature.main.utility.ConnectionUtility;

import java.sql.*;

public class ClientDao {
  public Client addClient(Client client) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "INSERT INTO clients (first_name, last_name) VALUES (?, ?)";
      PreparedStatement prepped = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      prepped.setString(1, client.getFirstName());
      prepped.setString(2, client.getLastName());
      prepped.executeUpdate();
      ResultSet rs = prepped.getGeneratedKeys();
      rs.next();
      int generatedId = rs.getInt(1);

      return new Client(generatedId, client.getFirstName(), client.getLastName());
    }
  }
}
