package com.verba.language.parsing.tokens.operators.mathop;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class DivideOpToken extends MathOpToken {

  public DivideOpToken() {
    super("/");
  }

  @Override
  public int getPriorityLevel() {
    return 2;
  }
}
