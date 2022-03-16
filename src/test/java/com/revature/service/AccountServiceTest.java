package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.exception.AccountNotFoundException;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
  AccountDao mockedObj = mock(AccountDao.class);

  @Test
  public void testGetAllAccounts() throws SQLException {
    List<Account> testAccounts = new ArrayList<>();
    testAccounts.add(new Account(1, "Savings", 900, 1));
    testAccounts.add(new Account(1, "Checking", 250, 1));
    testAccounts.add(new Account(1, "Savings", 2500, 1));

    when(mockedObj.getAllAccounts(1)).thenReturn(testAccounts);

    AccountService accountService = new AccountService(mockedObj);
    List<Account> actual = accountService.getAllAccounts("1");
    List<Account> expected = new ArrayList<>(testAccounts);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testGetAccountById() throws SQLException, AccountNotFoundException {
    Account testAccount = new Account(12, "Savings", 4756, 1);
    when(mockedObj.getAccountById(1, 12)).thenReturn(testAccount);

    AccountService accountService = new AccountService(mockedObj);
    Account actual = accountService.getAccountById("1", "12");
    Account expected = new Account(12, "Savings", 4756, 1);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testGetAccountById_doesNotExist() throws SQLException, AccountNotFoundException {
    AccountService accountService = new AccountService(mockedObj);
    Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById("24", "2"));
  }

  @Test
  public void testGetAccountById_invalidEntry() throws SQLException, AccountNotFoundException {
    AccountService accountService = new AccountService(mockedObj);

    try{
      accountService.getAccountById("test", "test");
      fail();
    } catch (IllegalArgumentException e) {
      String expectedMessage = "id provided must be a valid int";
      String actualMessage = e.getMessage();
      Assertions.assertEquals(expectedMessage, actualMessage);
    }
  }

  @Test
  public void testGetAccountById_sqlException() throws SQLException {
    when(mockedObj.getAccountById(anyInt(), anyInt())).thenThrow(SQLException.class);
    AccountService accountService = new AccountService(mockedObj);
    Assertions.assertThrows(SQLException.class, () -> { accountService.getAccountById("55", "12");});
  }

  @Test
  public void testAddAccount_confirm() throws SQLException {
    Account testAccount = new Account(0, "Savings", 250);
    when(mockedObj.addAccount(2, testAccount))
        .thenReturn(new Account(0, "Savings", 250, 2));

    AccountService accountService = new AccountService(mockedObj);

    Account actual = accountService.addAccount("2", testAccount);
    Account expected = new Account(0, "Savings", 250, 2);
    Assertions.assertEquals(expected, actual);
  }
}
