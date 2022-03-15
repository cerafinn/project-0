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

  @Override
  public void mapEndpoints(Javalin app) {
    app.get("/clients/{clientId}/accounts", getAllAccounts);
  }
}
