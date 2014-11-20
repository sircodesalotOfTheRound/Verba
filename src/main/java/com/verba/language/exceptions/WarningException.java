package com.verba.language.exceptions;

/**
 * Created by sircodesalot on 14-4-14.
 */
public class WarningException extends CompilerException {
  public WarningException(String message, Object... args) {
    super(message, args);
  }
}
