package com.verba.language.graph.validation.validation.parameters;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.validation.violations.ValidationError;
import com.verba.language.graph.validation.violations.ValidationViolation;
import com.verba.language.graph.validation.violations.ValidationViolationList;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class GenericParameterValidator extends ExpressionValidator {
//  public ValidationViolationList verifyAllParametersExplicitlyTyped() {
//    QIterable<ValidationViolation> violations = this.genericParameters()
//      .where(parameter -> !parameter.hasTypeConstraint())
//      .map(parameter -> {
//        return new ValidationError(parameter, "Generic parameter %s must have type constraint",
//          parameter.representation());
//      });
//
//    return new ValidationViolationList(violations);
//  }
}
