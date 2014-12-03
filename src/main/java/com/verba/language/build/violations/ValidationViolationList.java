package com.verba.language.build.violations;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.VerbaExpression;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class ValidationViolationList implements QIterable<ValidationViolation> {
  private final QList<ValidationViolation> violations = new QList<>();

  public ValidationViolationList() {
  }

  public ValidationViolationList(Iterable<ValidationViolation> violations) {
    this.add(violations);
  }

  public void add(ValidationViolation violation) {
    this.violations.add(violation);
  }

  public void add(Iterable<ValidationViolation> violations) {
    this.violations.add(violations);
  }

  public void addError(VerbaExpression expression, String format, Object... args) {
    ValidationViolation violation = new ValidationError(expression, format, args);
    this.add(violation);
  }

  public void addWarning(VerbaExpression expression, String format, Object... args) {
    ValidationViolation violation = new ValidationError(expression, format, args);
    this.add(violation);
  }

  @Override
  public Iterator<ValidationViolation> iterator() {
    return this.violations.iterator();
  }
}
