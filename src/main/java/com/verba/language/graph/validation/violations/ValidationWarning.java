package com.verba.language.graph.validation.violations;


import com.verba.language.parsing.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/10/13.
 */
public class ValidationWarning extends ValidationViolation {
  public ValidationWarning(VerbaExpression expression, String format, Object... args) {
    super(expression, format, args);
  }
}
