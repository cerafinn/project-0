package com.revature.exception;

public class AccountNotFoundException extends Exception {
  public AccountNotFoundException() {}

  public AccountNotFoundException(String message) {
    super(message);
  }

  public AccountNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccountNotFoundException(Throwable cause) {
    super(cause);
  }

  public AccountNotFoundException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
    super(message, cause, enableSuppresion, writableStackTrace);
  }
}
