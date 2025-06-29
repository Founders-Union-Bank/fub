package org.fub.exception;

public class JWTTokenCreationException extends RuntimeException {
  public JWTTokenCreationException(String message) {
    super(message);
  }
}
