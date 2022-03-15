package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.exception.AccountNotFoundException;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
  //create service layer for account
  private static Logger logger = LoggerFactory.getLogger(AccountService.class);

  private AccountDao accountDao;
  public AccountService() { this.accountDao = new AccountDao(); }
  public AccountService(AccountDao mockDao) { this.accountDao = mockDao; }

  private ClientDao clientDao;

  public List<Account> getAllAccounts(String id) throws SQLException {
    int clientId = Integer.parseInt(id);
    return this.accountDao.getAllAccounts(clientId);
  }

  public Account getAccountById(String cId, String aId) throws SQLException, AccountNotFoundException {
    try {
      int clientId = Integer.parseInt(cId);
      int accountId = Integer.parseInt(aId);
      Account a = accountDao.getAccountById(clientId, accountId);
      if (a == null) {
        throw new AccountNotFoundException("Account with id " + accountId + " not found");
      }
      return a;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("id provided must be a valid int");
    }
  }

  public List<Account> getAccountsByBalance(String lower, String upper) throws SQLException, AccountNotFoundException {
    try {
      int lowerLimit = Integer.parseInt(lower);
      int upperLimit = Integer.parseInt(upper);
      List<Account> accounts = accountDao.filterByBalance(lowerLimit, upperLimit);
      if (accounts == null) {
        throw new AccountNotFoundException("Account with balances between " + lowerLimit + " and " + upperLimit + " not found");
      }
      return accounts;
    } catch(NumberFormatException e) {
      throw new IllegalArgumentException("Limits provided must be valid ints");
    }
  }

  public Account addAccount(Account a) throws SQLException {
    validateAccountInfo(a);
    Account newAccount = accountDao.addAccount(a);
    return newAccount;
  }

  public Account editAccount(String aId, String cId, Account a) throws SQLException, AccountNotFoundException {
    try {
      int accountId = Integer.parseInt(aId);
      int clientId = Integer.parseInt(cId);

      if(accountDao.getAccountById(clientId, accountId) == null) {
        throw new AccountNotFoundException("Account with " + accountId + " does not exist for client with id " + clientId);
      }
      validateAccountInfo(a);
      a.setId(accountId);
      Account updatedAccount = accountDao.updateAccount(a);
      return updatedAccount;
    } catch(NumberFormatException e) {
      throw new IllegalArgumentException("id provided must be a valid int");
    }
  }

  public boolean deleteAccount(String aId, String cId) throws SQLException, AccountNotFoundException, ClientNotFoundException {
    try {
      int accountId = Integer.parseInt(aId);
      int clientId = Integer.parseInt(cId);

      if(accountDao.getAccountById(clientId, accountId) == null) {
        throw new AccountNotFoundException("Account with " + accountId + " does not exist.");
      } else if (clientDao.getClientById(clientId) == null) {
        throw new ClientNotFoundException("Client with " + clientId + " does not exist");
      }
      boolean result = accountDao.deleteAccount(accountId);
      return result;
    } catch(NumberFormatException e) {
      throw new IllegalArgumentException("id provided must be a valid int");
    }
  }

  private void validateAccountInfo(Account a) {

  }
}
