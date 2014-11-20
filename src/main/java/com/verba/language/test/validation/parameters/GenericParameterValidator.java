package com.verba.language.test.validation.parameters;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.test.validation.ExpressionValidator;
import com.verba.language.test.validation.violations.ValidationError;
import com.verba.language.test.validation.violations.ValidationViolation;
import com.verba.language.test.validation.violations.ValidationViolationList;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class GenericParameterValidator extends ExpressionValidator<GenericTypeListExpression> {
  public GenericParameterValidator(GenericTypeListExpression genericParameters) {
    super(genericParameters);
  }

  public ValidationViolationList verifyAllParametersExplicitlyTyped() {
    QIterable<ValidationViolation> violations = this.genericParameters()
      .where(parameter -> !parameter.hasTypeConstraint())
      .map(parameter -> {
        return new ValidationError(parameter, "Generic parameter %s must have type constraint",
          parameter.representation());
      });

    return new ValidationViolationList(violations);
  }

  public GenericTypeListExpression genericParameters() {
    return super.target();
  }
}
