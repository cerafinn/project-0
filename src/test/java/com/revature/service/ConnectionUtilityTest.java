package com.revature.service;

import com.revature.main.utility.ConnectionUtility;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ConnectionUtilityTest {
  @Test
  public void test_getConnection() throws SQLException {
    ConnectionUtility.getConnection();
  }
}