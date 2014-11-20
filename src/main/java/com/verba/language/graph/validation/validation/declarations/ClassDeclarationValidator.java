package com.verba.language.graph.validation.validation.declarations;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.validation.validation.TupleListDeclarationValidator;
import com.verba.language.graph.validation.validation.fqn.FullyQualifiedNameValidator;
import com.verba.language.graph.validation.violations.ValidationError;
import com.verba.language.graph.validation.violations.ValidationViolation;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;

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
        this.addError((VerbaExpression) this.classDeclaration().traits(), "Class base type cannot be tuple.");
      }


      // Validate generic parameterSets for base type.
      for (FullyQualifiedNameExpression fqn :
        this.classDeclaration().traits().ofType(FullyQualifiedNameExpression.class)) {
        FullyQualifiedNameValidator validator = new FullyQualifiedNameValidator(fqn);
        QIterable<NamedValueExpression> genericParametersWithConstraint = validator
          .flattenedGenericParameterList()
          .where(NamedValueExpression::hasTypeConstraint);

        for (NamedValueExpression invlidParameter : genericParametersWithConstraint) {
          this.addError(invlidParameter, "Generic Parameters in base types cannot be constained.", invlidParameter);
        }

      }
    }
  }

  public void validateName() {
    if (!declarationValidator.hasSingleMember()) {
      String representation = classDeclaration().declaration().representation();
      this.addError(this.classDeclaration().declaration(),
        "Class '%s' is not a valid declaration name.", representation);
    }


  }

  private void validateParameters() {
    // Validate the number of parameterSets
    if (declarationValidator.hasParameters()) {
      if (this.classDeclaration().inlineParameters().count() > 1) {
        this.addError(this.classDeclaration().inlineParameters().first(),
          "Inline class declarations cannot have multiple sets of arguments.");
      }

    }

    // Validate the types of parameterSets
    QIterable<ValidationViolation> typeViolations = new TupleListDeclarationValidator(this.classDeclaration().inlineParameters())
      .findArguments(parameter -> !(parameter instanceof NamedValueExpression))
      .map(parameter -> new ValidationError(parameter, "Parameter %s must be a variable declaration", parameter));

    // Validate that the parameterSets are constrained

    QIterable<ValidationViolation> missingConstraintViolations = declarationValidator
      .flattenedParameterList()
      .ofType(NamedValueExpression.class)
      .where(parameter -> !parameter.hasTypeConstraint())
      .map(parameter -> new ValidationError(parameter, "Parameter %s must have a type constraint", parameter));

    super.addViolations(typeViolations);
    super.addViolations(missingConstraintViolations);
  }

  public ClassDeclarationExpression classDeclaration() {
    return super.target();
  }
}
