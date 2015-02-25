package com.verba.language.parse.tokens.operators.mathop;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class DivideOpToken extends InfixOperatorToken {

  public DivideOpToken() {
    super("/");
  }

  @Override
  public int getPriorityLevel() {
    return 2;
  }
}
