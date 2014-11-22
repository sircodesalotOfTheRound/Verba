package com.verba.language.graph.validation.validation.declarations;

import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.validation.validation.fqn.FullyQualifiedNameValidator;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;

/**
 * Created by sircodesalot on 14-5-3.
 */
public class FunctionDeclarationValidator extends ExpressionValidator {
  public void validate() {
    this.validateName();
//    this.validateParameters();
    this.validateReturnValue();
  }

  private void validateReturnValue() {
//        if (this.function().returnType() != null) {
//            if (this.function().returnType() instanceof TupleDeclarationExpression) {
//
//            }
//        }
  }

//  private void validateParameters() {
//    for (TupleDeclarationExpression tuple : this.function().parameterSets()) {
//      validateParameterTuple(tuple);
//    }
//  }

  private void validateParameterTuple(TupleDeclarationExpression tuple) {
    for (VerbaExpression expression : tuple.items()) {
      if (expression instanceof NamedValueExpression) {
        NamedValueExpression varName = (NamedValueExpression) expression;

        if (!varName.hasTypeConstraint()) {
          this.addError(expression,
            "The parameter '%s' must have a type constraint.",
            varName.identifier().representation());
        }
      } else {
        this.addError(expression, "Expression %s is not a valid VarNameExpression", expression);
      }
    }
  }

  public void validateName() {
    /*
    if (!declarationValidator.hasSingleMember()) {
      this.addError(this.function().declaration(),
        "Function '%s' is not a valid declaration name.", function().declaration().representation());
    }

    if (!declarationValidator.hasParameters()) {
      this.addError(this.function().declaration(),
        "Function '%s' must have parameterSets.", function().declaration().representation());
    }*/
  }
}
