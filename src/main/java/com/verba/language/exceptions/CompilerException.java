package com.verba.language.exceptions;

/**
 * Created by sircodesalot on 14-4-14.
 */

@Deprecated
public class CompilerException extends RuntimeException {
  public CompilerException(String message, Object... args) {
    super(String.format(message, args));
  }
}
