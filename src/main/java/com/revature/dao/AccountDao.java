package com.revature.dao;

import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
  public Account addAccount(int clientId, Account account) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "INSERT INTO accounts (account_type, balance, client_id) VALUES (?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, account.getAccountType());
      ps.setInt(2, account.getBalance());
      ps.setInt(3, clientId);

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int generatedId = rs.getInt(1);

      return new Account(generatedId, account.getAccountType(), account.getBalance(), account.getClientId());
    }
  }

  //get one
  public Account getAccountById(int clientId, int accountId) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM accounts WHERE id = ? AND client_id = ?";
      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setInt(1, accountId);
      ps.setInt(2, clientId);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        String accountType = rs.getString("account_type");
        int balance = rs.getInt("balance");

        return new Account(accountId, accountType, balance, clientId);
      }
    }
    return null;
  }

  //get all
  public List<Account> getAllAccounts(int clientId) throws SQLException {
    List<Account> accounts = new ArrayList<>();

    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM accounts WHERE client_id = ?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, clientId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String accountType = rs.getString("account_type");
        int balance = rs.getInt("balance");

        accounts.add(new Account(id, accountType, balance, clientId));
      }
    }
    return accounts;
  }

  //get by balance constraint
  public List<Account> filterByBalance(int clientId, int lowerLimit, int upperLimit) throws SQLException {
    List<Account> accounts = new ArrayList<>();

    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM accounts WHERE client_id = ? AND accounts.balance BETWEEN ? AND ?";
      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setInt(1, clientId);
      ps.setInt(2, lowerLimit);
      ps.setInt(3, upperLimit);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        int id = rs.getInt("id");
        String accountType = rs.getString("account_type");
        int balance = rs.getInt("balance");

        accounts.add(new Account(id, accountType, balance, clientId));
      }
    }
    return accounts;
  }

  //update
  public Account updateAccount(int accountId, Account account) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "UPDATE accounts SET account_type = ?, balance = ? WHERE id = ?";

      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setString(1, account.getAccountType());
      ps.setInt(2, account.getBalance());
      ps.setInt(3, accountId);
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
