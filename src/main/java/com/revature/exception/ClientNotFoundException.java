package com.revature.exception;

public class ClientNotFoundException extends Exception {
  public ClientNotFoundException() {};

  public ClientNotFoundException(String message) {
    super(message);
  }

  public ClientNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientNotFoundException(Throwable cause) {
    super(cause);
  }

  public ClientNotFoundException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
    super(message, cause, enableSuppresion, writableStackTrace);
  }
}
