package com.verba.language.exceptions;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class ParseException extends CompilerException {
  public static ParseException INSTANCE = new ParseException("Parsing Exception");

  public ParseException(String message, Object... args) {
    super(message, args);
  }
}
