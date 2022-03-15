package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.model.Account;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AccountServiceTest {
  @Test
  public void testGetAllAccounts() throws SQLException {
    AccountDao mockedObj = mock(AccountDao.class);

    List<Account> testAccounts = new ArrayList<>();
    testAccounts.add(new Account(1, "Savings", 900, 1));
    testAccounts.add(new Account(1, "Checking", 250, 1));
    testAccounts.add(new Account(1, "Savings", 900, 2));
    testAccounts.add(new Account(1, "Checking", 900, 2));
    testAccounts.add(new Account(1, "Checking", 900, 3));

  }
}
