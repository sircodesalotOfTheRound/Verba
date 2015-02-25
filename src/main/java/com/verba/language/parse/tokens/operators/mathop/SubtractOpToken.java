package com.verba.language.parse.tokens.operators.mathop;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class SubtractOpToken extends InfixOperatorToken {

  public SubtractOpToken() {
    super("-");
  }

  @Override
  public int getPriorityLevel() {
    return 1;
  }
}
