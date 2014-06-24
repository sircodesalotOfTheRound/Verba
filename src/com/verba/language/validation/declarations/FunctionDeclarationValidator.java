package com.verba.language.validation.declarations;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.validation.ExpressionValidator;
import com.verba.language.validation.fqn.FullyQualifiedNameValidator;

/**
 * Created by sircodesalot on 14-5-3.
 */
public class FunctionDeclarationValidator extends ExpressionValidator<FunctionDeclarationExpression> {
    private final FullyQualifiedNameValidator declarationValidator;

    public FunctionDeclarationValidator(FunctionDeclarationExpression function) {
        super(function);

        this.declarationValidator = new FullyQualifiedNameValidator(function.declaration());
    }

    public void validate() {
        this.validateName();
        this.validateParameters();
        this.validateReturnValue();
    }

    private void validateReturnValue() {
//        if (this.function().returnType() != null) {
//            if (this.function().returnType() instanceof TupleDeclarationExpression) {
//
//            }
//        }
    }

    private void validateParameters() {
        for (TupleDeclarationExpression tuple : this.function().parameters()) {
            validateParameterTuple(tuple);
        }
    }

    private void validateParameterTuple(TupleDeclarationExpression tuple) {
        for (VerbaExpression expression : tuple.items()) {
            if (expression instanceof VarNameDeclarationExpression) {
                VarNameDeclarationExpression varName = (VarNameDeclarationExpression) expression;

                if (!varName.hasTypeConstraint()) {
                    this.addViolation(expression,
                        "The parameter '%s' must have a type constraint.",
                        varName.identifier().representation());
                }
            } else {
                this.addViolation(expression, "Expression %s is not a valid VarNameExpression", expression);
            }
        }
    }

    public void validateName() {
        if (!declarationValidator.hasSingleMember()) {
            this.addViolation(this.function().declaration(),
                "Function '%s' is not a valid declaration name.", function().declaration().representation());
        }

        if (!declarationValidator.hasParameters()) {
            this.addViolation(this.function().declaration(),
                "Function '%s' must have parameters.", function().declaration().representation());
        }
    }

    public FunctionDeclarationExpression function() {
        return super.target();
    }
}
