package com.revature.controller;

import com.revature.exception.AccountNotFoundException;
import com.revature.exception.ClientNotFoundException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionController implements Controller {
  //create exceptions for:
  //client not found
  //invalid entries
  //account not found

  private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

  private ExceptionHandler clientNotFound = (e, ctx) -> {
    logger.warn("Client attempted to retrieve does not exist: " + e.getMessage());
    ctx.status(404);
    ctx.json(e.getMessage());
  };

  private ExceptionHandler accountNotFound = (e, ctx) -> {
    logger.warn("Account attempted to retrieve does not exist: " + e.getMessage());
    ctx.status(404);
    ctx.json(e.getMessage());
  };

  private ExceptionHandler badArgument = (e, ctx) -> {
    logger.warn("User input a bad argument. Exception message is " + e.getMessage());
    ctx.status(400);
    ctx.json(e.getMessage());
  };

  @Override
  public void mapEndpoints(Javalin app) {
    app.exception(ClientNotFoundException.class, clientNotFound);
    app.exception(AccountNotFoundException.class, accountNotFound);
    app.exception(IllegalArgumentException.class, badArgument);
  }
}
