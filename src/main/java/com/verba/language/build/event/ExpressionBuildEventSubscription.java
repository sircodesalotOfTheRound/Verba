package com.verba.language.build.event;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.violations.ValidationError;
import com.verba.language.build.violations.ValidationViolation;
import com.verba.language.build.violations.ValidationViolationList;
import com.verba.language.build.violations.ValidationWarning;
import com.verba.language.parse.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/11/22.
 */
public abstract class ExpressionBuildEventSubscription<T> implements BuildEventSubscriptionBase {
  private final ValidationViolationList violations = new ValidationViolationList();
  private final T expression;

  public ExpressionBuildEventSubscription(T expression) {
    this.expression = expression;
  }

  public T expression() { return this.expression; }

  public void addErrorViolation(VerbaExpression expression, String format, Object... args) {
    this.violations.addError(expression, format, args);
  }

  public void addWarningViolation(VerbaExpression expression, String format, Object... args) {
    this.violations.addWarning(expression, format, args);
  }

  public QIterable<ValidationViolation> violations() { return this.violations; }
  public QIterable<ValidationError> errors() { return this.violations.ofType(ValidationError.class); }
  public QIterable<ValidationWarning> warnings() { return this.violations.ofType(ValidationWarning.class); }
}
