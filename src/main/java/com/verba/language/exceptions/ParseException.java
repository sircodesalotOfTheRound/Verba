package com.verba.language.exceptions;

/**
 * Created by sircodesalot on 14-2-16.
 */
@Deprecated
public class ParseException extends CompilerException {
  public ParseException(String message, Object... args) {
    super(message, args);
  }
}
