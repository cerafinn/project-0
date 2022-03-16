package com.revature.model;

import java.util.Objects;

public class Account {
  //create columns for account, along with getters and setters
  private int id;
  private String accountType;
  private int balance;
  private int clientId; // should this be a call to getclient by id given endpoint?

  public Account() {};

  public Account(String accountType, int balance) {
    this.accountType = accountType;
    this.balance = balance;
  }

  public Account(int id, String accountType, int balance) {
    this.id = id;
    this.accountType = accountType;
    this.balance = balance;
  }

  public Account(int id, String accountType, int balance, int clientId) {
    this.id = id;
    this.accountType = accountType;
    this.balance = balance;
    this.clientId = clientId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if( obj ==null || getClass() != obj.getClass()) return false;

    Account account = (Account) obj;
    return id == account.id && balance == account.balance && clientId == account.clientId && Objects.equals(accountType, account.accountType);
  }

  @Override
  public int hashCode() { return Objects.hash(id, accountType, balance, clientId); }

  @Override
  public String toString() {
    return "Account{" + "id=" + id + ", accountType='" + accountType + '\'' + ", balance='" + balance + '\'' + ", clientId='" + clientId + '}';
  }
}
