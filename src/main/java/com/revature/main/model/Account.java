package com.revature.main.model;

import java.util.Objects;

public class Account {
  //create columns for account, along with getters and setters
  private int id;
  private String type;
  private int balance;
  private int clientId;

  public Account() {};
  public Account(int id, String type, int balance, int clientId) {
    this.id = id;
    this.type = type;
    this.balance = balance;
    this.clientId = clientId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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
    return id == account.id && balance == account.balance && clientId == account.clientId && Objects.equals(type, account.type);
  }

  @Override
  public int hashCode() { return Objects.hash(id, type, balance, clientId); }

  @Override
  public String toString() {
    return "Account{" + "id=" + id + ", type='" + type + '\'' + ", balance='" + balance + '\'' + ", clientId='" + clientId + '}';
  }
}
