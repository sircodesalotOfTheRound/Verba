package com.verba.language.parse.tokens.operators.mathop;

import com.verba.language.parse.tokens.operators.assignment.CompositeAssignmentToken;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class PowerEqualsToken extends CompositeAssignmentToken {
  public PowerEqualsToken() {
    super("^=");
  }
}
