package com.verba.language.graph.validation;


import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.violations.ValidationViolation;
import com.verba.language.parse.violations.ValidationViolationList;
import com.verba.language.parse.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14-5-3.
 */
public abstract class ExpressionValidator {
  private final ValidationViolationList violations = new ValidationViolationList();

  protected void addViolations(Iterable<ValidationViolation> violations) {
    for (ValidationViolation violation : violations) {
      this.addViolation(violation);
    }
  }

  protected void addViolation(ValidationViolation violation) {
    this.violations.add(violation);
  }

  protected void addError(VerbaExpression expression, String format, Object... args) {
    this.violations.addError(expression, format, args);
  }

  protected void addWarning(VerbaExpression expression, String format, Object... args) {
    this.violations.addWarning(expression, format, args);
  }

  public QIterable<ValidationViolation> violations() {
    return this.violations;
  }
}
