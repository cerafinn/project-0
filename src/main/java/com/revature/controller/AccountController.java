package com.revature.controller;

import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

public class AccountController implements Controller {
  //create routes for accounts from queries -- need to fetch client id for endpoints
  public AccountService accountService;

  public AccountController() { this.accountService = new AccountService(); }

  private Handler getAllAccounts = (ctx) -> {
    String clientId = ctx.pathParam("clientId");
    String amountLessThan = ctx.queryParam("amountLessThan");
    String amountGreaterThan = ctx.queryParam("amountGreaterThan");

    Map<String, List<String>> queryMap = ctx.queryParamMap();
    if (queryMap.containsKey("amountLessThan") && queryMap.containsKey("amountGreaterThan")) {
      List<Account> accounts = accountService.getAccountsByBalance(clientId, amountLessThan, amountGreaterThan);
      ctx.json(accounts);
    } else {
      List<Account> accounts = accountService.getAllAccounts(clientId);
      ctx.json(accounts);
    }
  };

  private Handler getAccountById = (ctx) -> {
    String clientId = ctx.pathParam("clientId");
    String accountId = ctx.pathParam("accountId");
    Account account = accountService.getAccountById(clientId, accountId);
    ctx.json(account);
  };

  private Handler addAccount = (ctx) -> {
    String clientId = ctx.pathParam("clientId");
    Account newAccount = ctx.bodyAsClass(Account.class);
    Account account = accountService.addAccount(clientId, newAccount);
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
    String clientId = ctx.pathParam("clientId");
    String accountId = ctx.pathParam("accountId");
    boolean result = accountService.deleteAccount(accountId, clientId);
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
