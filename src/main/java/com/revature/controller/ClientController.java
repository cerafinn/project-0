package com.revature.controller;

import com.revature.model.Client;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class ClientController implements Controller {
  //create routes for clients from queries
  public ClientService clientService;

  public ClientController() { this.clientService = new ClientService(); }

  private Handler getAllClients = (ctx) -> {
    List<Client> clients = clientService.getAllClients();
    ctx.json(clients);
  };

  private Handler getClientById = (ctx) -> {
    String id = ctx.pathParam("clientId");
    Client client = clientService.getClientById(id);
    ctx.json(client);
  };

  private Handler addClient = (ctx) -> {
    Client newClient = ctx.bodyAsClass(Client.class);
    Client client = clientService.addClient(newClient);
    ctx.status(201);
    ctx.json(client);
  };

  private Handler updateClient = (ctx) -> {
    Client updatedClient = ctx.bodyAsClass(Client.class);
    Client client = clientService.editClient(ctx.pathParam("clientId"), updatedClient);
    ctx.status(200);
    ctx.json(client);
  };

  private Handler deleteClient = (ctx) -> {
    boolean result = clientService.deleteClient(ctx.pathParam("clientId"));
    ctx.status(200);
    ctx.json(result);
    // may need to test/refactor delete
  };

  @Override
  public void mapEndpoints(Javalin app) {
    app.get("/clients", getAllClients);
    app.get("/clients/{clientId}", getClientById);
    app.post("/clients", addClient);
    app.put("/clients/{clientId}", updateClient);
    app.delete("/clients/{clientId}", deleteClient);
  }
}
