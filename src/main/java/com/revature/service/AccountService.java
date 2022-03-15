package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.AccountNotFoundException;
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

  public List<Account> getAllAccounts() throws SQLException {
    return this.accountDao.getAllAccounts();
  }

  public Account getAccountById(String id) throws SQLException, AccountNotFoundException {
    try {
      int accountId = Integer.parseInt(id);
      Account a = accountDao.getAccountById(accountId);
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

  public Account editAccount(String id, Account a) throws SQLException, AccountNotFoundException {
    try {
      int accountId = Integer.parseInt(id);

      if(accountDao.getAccountById(accountId) == null) {
        throw new AccountNotFoundException("Account with " + accountId + " does not exist.");
      }
      validateAccountInfo(a);
      a.setId(accountId);
      Account updatedAccount = accountDao.updateAccount(a);
      return updatedAccount;
    } catch(NumberFormatException e) {
      throw new IllegalArgumentException("id provided must be a valid int");
    }
  }

  public boolean deleteAccount(String id) throws SQLException, AccountNotFoundException {
    try {
      int accountId = Integer.parseInt(id);

      if(accountDao.getAccountById(accountId) == null) {
        throw new AccountNotFoundException("Account with " + accountId + " does not exist.");
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
