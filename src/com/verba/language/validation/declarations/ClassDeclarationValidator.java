package com.verba.language.validation.declarations;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.validation.ExpressionValidator;
import com.verba.language.validation.TupleListDeclarationValidator;
import com.verba.language.validation.fqn.FullyQualifiedNameValidator;
import com.verba.language.validation.violations.ValidationViolation;

/**
 * Created by sircodesalot on 14-5-3.
 */
public class ClassDeclarationValidator extends ExpressionValidator<ClassDeclarationExpression> {
    private final FullyQualifiedNameValidator declarationValidator;

    public ClassDeclarationValidator(ClassDeclarationExpression expression) {
        super(expression);

        this.declarationValidator = new FullyQualifiedNameValidator(expression.declaration());
    }

    public void validate() {
        this.validateName();
        this.validateParameters();
        this.validateBaseType();
    }

    private void validateBaseType() {
        if (this.classDeclaration().hasTraits()) {
            if (this.classDeclaration().traits().any(base -> base instanceof TupleDeclarationExpression)) {
                this.addViolation((VerbaExpression) this.classDeclaration().traits(), "Class base type cannot be tuple.");
            }


            // Validate generic parameters for base type.
            for (FullyQualifiedNameExpression fqn :
                this.classDeclaration().traits().ofType(FullyQualifiedNameExpression.class)) {
                FullyQualifiedNameValidator validator = new FullyQualifiedNameValidator(fqn);
                QIterable<VarNameDeclarationExpression> genericParametersWithConstraint = validator
                    .flattenedGenericParameterList()
                    .where(VarNameDeclarationExpression::hasTypeConstraint);

                for (VarNameDeclarationExpression invlidParameter : genericParametersWithConstraint) {
                    this.addViolation(invlidParameter, "Generic Parameters in base types cannot be constained.", invlidParameter);
                }

            }
        }
    }

    public void validateName() {
        if (!declarationValidator.hasSingleMember()) {
            String representation = classDeclaration().declaration().representation();
            this.addViolation(this.classDeclaration().declaration(),
                "Class '%s' is not a valid declaration name.", representation);
        }


    }

    private void validateParameters() {
        // Validate the number of parameters
        if (declarationValidator.hasParameters()) {
            if (this.classDeclaration().inlineParameters().count() > 1) {
                this.addViolation(this.classDeclaration().inlineParameters().first(),
                    "Inline class declarations cannot have multiple sets of arguments.");
            }

        }

        // Validate the types of parameters
        QIterable<ValidationViolation> typeViolations = new TupleListDeclarationValidator(this.classDeclaration().inlineParameters())
            .findArguments(parameter -> !(parameter instanceof VarNameDeclarationExpression))
            .map(parameter -> new ValidationViolation(parameter, "Parameter %s must be a variable declaration", parameter));

        // Validate that the parameters are constrained

        QIterable<ValidationViolation> missingConstraintViolations = declarationValidator
            .flattenedParameterList()
            .ofType(VarNameDeclarationExpression.class)
            .where(parameter -> !parameter.hasTypeConstraint())
            .map(parameter -> new ValidationViolation(parameter, "Parameter %s must have a type constraint", parameter));

        super.addViolations(typeViolations);
        super.addViolations(missingConstraintViolations);
    }

    public ClassDeclarationExpression classDeclaration() {
        return super.target();
    }
}
