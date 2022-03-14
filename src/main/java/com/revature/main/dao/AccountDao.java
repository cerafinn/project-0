package com.revature.main.dao;

import com.revature.main.model.Account;
import com.revature.main.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
  //add
  //get all
  //get one
  //update
  //delete

  public Account addAccount(Account account) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "INSERT INTO accounts (type, balance) VALUES (?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, account.getType());
      ps.setInt(2, account.getBalance());
      //how to set client id automatically?
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int generatedId = rs.getInt(1);

      return new Account(generatedId, account.getType(), account.getBalance(), account.getClientId());
    }
  }

  //get one
  public Account getAccountId(int id) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM accounts WHERE id = ?";
      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        String type = rs.getString("type");
        int balance = rs.getInt("balance");
        // get client id/id information

        return new Account(id, type, balance);
      }
    }
    return null;
  }

  //get all
  public List<Account> getAllAccounts() throws SQLException {
    List<Account> accounts = new ArrayList<>();

    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM accounts";
      PreparedStatement ps = connection.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String type = rs.getString("type");
        int balance = rs.getInt("balance");
        //need return for client information?

        accounts.add(new Account(id, type, balance));
      }
    }
    return accounts;
  }

  //update
  public Account updateAccount(Account account) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "UPDATE accounts " + "SET type = ?, " + "balance = ?, " + "WHERE id = ?";

      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setString(1, account.getType());
      ps.setInt(2, account.getBalance());
      ps.setInt(4, account.getId());

      ps.executeUpdate();
    }
    return account;
  }

  //delete
  public boolean deleteAccount(int id) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "DELETE FROM accounts WHERE id = ?";
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
