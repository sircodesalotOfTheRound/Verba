package com.verba.language.parsing.expressions.backtracking;

/**
 * Created by sircodesalot on 14-2-20.
 */
public class MismatchException extends RuntimeException {
  private static final MismatchException instance = new MismatchException();

  private MismatchException() {
  }

  public static MismatchException getInstance() {
    return MismatchException.instance;
  }
}
