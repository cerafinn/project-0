package com.revature.controller;

import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class AccountController implements Controller {
  //create routes for accounts from queries
  public AccountService accountService;

  public AccountController() { this.accountService = new AccountService(); }

  private Handler getAllAccounts = (ctx) -> {
    List<Account> accounts = accountService.getAllAccounts();
    ctx.json(accounts);
  };

  private Handler getAccountById = (ctx) -> {
    String id = ctx.pathParam("accountId");
    Account account = accountService.getAccountById(id);
    ctx.json(account);
  };

  private Handler addAccount = (ctx) -> {
    Account newAccount = ctx.bodyAsClass(Account.class);
    Account account = accountService.addAccount(newAccount);
    ctx.status(201);
    ctx.json(account);
  };

  private Handler editAccount = (ctx) -> {
    Account accountToEdit = ctx.bodyAsClass(Account.class);
    Account account = accountService.editAccount(ctx.pathParam("accountId"), accountToEdit);
    ctx.status(200);
    ctx.json(account);
  };

  private Handler deleteAccount = (ctx) -> {
    boolean result = accountService.deleteAccount(ctx.pathParam("accountId"));
    ctx.status(200);
    ctx.json(result);
  };

  @Override
  public void mapEndpoints(Javalin app) {
    app.get("/clients/{clientId}/accounts", getAllAccounts);
    app.get("/clients/{clientId}/accounts/{accountId}", getAccountById);
    app.post("/clients/{clientId}/accounts", addAccount);
    app.put("/clients/{clientId}/accounts/{accountId}", editAccount);
    app.delete("/clients/{clientId}/accounts/{accountId}", deleteAccount);
  }
}
