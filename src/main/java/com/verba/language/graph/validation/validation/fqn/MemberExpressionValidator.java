package com.verba.language.graph.validation.validation.fqn;


import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.members.MemberExpression;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class MemberExpressionValidator extends ExpressionValidator<MemberExpression> {
  public MemberExpressionValidator(MemberExpression target) {
    super(target);
  }

  public boolean hasParameters() {
    return this.member().hasParameters();
  }

  public boolean hasGenericParameters() {
    return this.member().hasGenericParameters();
  }

  public boolean allParametersExplicitlyTypes() {
    QIterable<VerbaExpression> parameters = this.member()
      .parameterLists()
      .flatten(parameterList -> parameterList.items());

    boolean allParametersAreVarNameExpressions = parameters
      .all(parameter -> parameter instanceof NamedValueExpression);

    if (!allParametersAreVarNameExpressions) return false;

    return parameters
      .cast(NamedValueExpression.class)
      .all(NamedValueExpression::hasTypeConstraint);
  }

  public QIterable<VerbaExpression> flattenedParameterLists() {
    return this.member().parameterLists()
      .flatten(list -> list.items());
  }

  public MemberExpression member() {
    return super.target();
  }
}
