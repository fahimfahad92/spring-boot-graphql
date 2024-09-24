package com.rnd.springbootgraphql.exception;

public class AddressNotFound extends RuntimeException {
  public AddressNotFound(String message) {
    super(message);
  }
}
