package com.revature.model;

import java.util.List;
import java.util.Objects;

public class Client {
  // create columns for client, along with getters and setters
  private int id;
  private String firstName;
  private String lastName;
  private int age;
  private List<Account> accounts; //stackoverflow -- need to test and determine the best collection type

  //added more constructors, need to have an if accounts == null argument
  public Client() {};

  public Client(int id, String firstName, String lastName, int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public Client(int id, String firstName, String lastName, int age, List<Account> accounts) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.accounts = accounts;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if( obj ==null || getClass() != obj.getClass()) return false;

    Client client = (Client) obj;
    return id == client.id && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) ;
  }

  @Override
  public int hashCode() { return Objects.hash(id, firstName, lastName); }

  @Override
  public String toString() {
    return "Client{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '}';
  }
}