package com.verba.tools.exceptions;

/**
 * Created by sircodesalot on 14/12/4.
 */
public class CompilerException extends RuntimeException {
  public CompilerException(String message, Object ... args) {
    super(String.format(message, args));
  }
}
