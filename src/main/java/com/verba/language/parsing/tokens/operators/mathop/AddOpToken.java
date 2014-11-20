package com.verba.language.parsing.tokens.operators.mathop;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class AddOpToken extends MathOpToken {

  public AddOpToken() {
    super("+");
  }

  @Override
  public int getPriorityLevel() {
    return 1;
  }
}
