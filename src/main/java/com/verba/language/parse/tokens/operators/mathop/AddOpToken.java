package com.verba.language.parse.tokens.operators.mathop;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class AddOpToken extends InfixOperatorToken {

  public AddOpToken() {
    super("+");
  }

  @Override
  public int getPriorityLevel() {
    return 1;
  }
}
