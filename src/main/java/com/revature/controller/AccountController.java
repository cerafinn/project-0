package com.revature.controller;

import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class AccountController implements Controller {
  //create routes for accounts from queries -- need to fetch client id for endpoints
  public AccountService accountService;

  public AccountController() { this.accountService = new AccountService(); }

  private Handler getAllAccounts = (ctx) -> {
    String clientId = ctx.pathParam("clientId");
    List<Account> accounts = accountService.getAllAccounts(clientId);
    ctx.json(accounts);
  };

  private Handler getAccountById = (ctx) -> {
    String clientId = ctx.pathParam("clientId");
    String accountId = ctx.pathParam("accountId");
    Account account = accountService.getAccountById(clientId, accountId);
    ctx.json(account);
  };

  private Handler filterByBalance = (ctx) -> {
    String lower = ctx.pathParam("lowerLimit");
    String upper = ctx.pathParam("upperLimit");
    List<Account> accounts = accountService.getAccountsByBalance(lower, upper);
    ctx.json(accounts);
  };

  private Handler addAccount = (ctx) -> {
    Account newAccount = ctx.bodyAsClass(Account.class);
    Account account = accountService.addAccount(newAccount);
    ctx.status(201);
    ctx.json(account);
  };

  private Handler editAccount = (ctx) -> {
    Account accountToEdit = ctx.bodyAsClass(Account.class);
    Account account = accountService.editAccount(ctx.pathParam("accountId"), ctx.pathParam("clientId"), accountToEdit);
    ctx.status(200);
    ctx.json(account);
  };

  private Handler deleteAccount = (ctx) -> {
    boolean result = accountService.deleteAccount(ctx.pathParam("accountId"), ctx.pathParam("clientId"));
    ctx.status(200);
    ctx.json(result);
  };

  @Override
  public void mapEndpoints(Javalin app) {
    app.get("/clients/{clientId}/accounts", getAllAccounts);
    app.get("/clients/{clientId}/accounts/{accountId}", getAccountById);
    app.get("/clients/{clientId}/accounts?amountLessThan={lowerLimit}&amountGreaterThan={upperLimit}", filterByBalance);
    app.post("/clients/{clientId}/accounts", addAccount);
    app.put("/clients/{clientId}/accounts/{accountId}", editAccount);
    app.delete("/clients/{clientId}/accounts/{accountId}", deleteAccount);
  }
}
