package com.revature.dao;

import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
  public Account addAccount(Account account) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "INSERT INTO accounts (account_type, balance, client_id) VALUES (?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, account.getAccountType());
      ps.setInt(2, account.getBalance());
      ps.setInt(3, account.getClientId());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int generatedId = rs.getInt(1);

      return new Account(generatedId, account.getAccountType(), account.getBalance(), account.getClientId());
    }
  }

  //get one
  public Account getAccountById(int id) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM accounts WHERE id = ?";
      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        String accountType = rs.getString("account_type");
        int balance = rs.getInt("balance");
        int clientId = rs.getInt("clientId");

        return new Account(id, accountType, balance, clientId);
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
        String accountType = rs.getString("accountType");
        int balance = rs.getInt("balance");
        int clientId = rs.getInt("clientId");

        accounts.add(new Account(id, accountType, balance, clientId));
      }
    }
    return accounts;
  }

  //get by balance constraint
  public List<Account> filterByBalance(int lowerLimit, int upperLimit) throws SQLException {
    List<Account> accounts = new ArrayList<>();

    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "SELECT * FROM accounts WHERE accounts.balance BETWEEN amountLessThan = ? and amountGreaterThan = ?";
      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setInt(1, lowerLimit);
      ps.setInt(2, upperLimit);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        int id = rs.getInt("id");
        String accountType = rs.getString("account_type");
        int balance = rs.getInt("balance");
        int clientId = rs.getInt("clientId");

        accounts.add(new Account(id, accountType, balance, clientId));
      }
    }
    return accounts;
  }

  //update
  public Account updateAccount(Account account) throws SQLException {
    try (Connection connection = ConnectionUtility.getConnection()) {
      String sql = "UPDATE accounts " + "SET accountType = ?, " + "balance = ?, " + "WHERE id = ?";

      PreparedStatement ps = connection.prepareStatement(sql);

      ps.setString(1, account.getAccountType());
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
