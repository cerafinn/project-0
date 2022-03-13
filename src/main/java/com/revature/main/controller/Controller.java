package com.revature.main.controller;

import io.javalin.Javalin;

public interface Controller {
  public void mapEndpoints(Javalin app);
}
