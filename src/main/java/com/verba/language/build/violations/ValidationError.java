package com.verba.language.build.violations;

import com.verba.language.parse.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/10/13.
 */
public class ValidationError extends ValidationViolation {
  public ValidationError(VerbaExpression expression, String format, Object... args) {
    super(expression, format, args);
  }
}
