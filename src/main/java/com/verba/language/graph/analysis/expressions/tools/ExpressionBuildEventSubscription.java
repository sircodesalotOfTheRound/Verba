package com.verba.language.graph.analysis.expressions.tools;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.graph.validation.violations.ValidationError;
import com.verba.language.graph.validation.violations.ValidationViolation;
import com.verba.language.graph.validation.violations.ValidationViolationList;
import com.verba.language.graph.validation.violations.ValidationWarning;
import com.verba.language.parsing.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/11/22.
 */
public abstract class ExpressionBuildEventSubscription<T> {
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
