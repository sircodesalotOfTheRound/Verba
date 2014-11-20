package com.verba.language.graph.validation.validation.fqn;


import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.expressions.members.MemberExpression;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class FullyQualifiedNameValidator extends ExpressionValidator<FullyQualifiedNameExpression> {
  public FullyQualifiedNameValidator(FullyQualifiedNameExpression fqn) {
    super(fqn);
  }

  public boolean hasParameters() {
    return this.fqn().any(MemberExpression::hasParameters);
  }

  public boolean hasGenericParameters() {
    return this.fqn().any(MemberExpression::hasGenericParameters);
  }

  public boolean hasSingleMember() {
    return this.fqn().count() == 1;
  }

  public QIterable<MemberExpression> membersWithGenericParameters() {
    return this.fqn().members().where(MemberExpression::hasGenericParameters);
  }

  public QIterable<MemberExpression> membersWithParameters() {
    return this.fqn().members().where(MemberExpression::hasParameters);
  }

  public QIterable<VerbaExpression> flattenedParameterList() {
    // Flatten the parameter lists, then flatten all parameterSets
    return this.membersWithParameters()
      .flatten(MemberExpression::parameterLists)
      .flatten(tuple -> tuple.items());
  }

  public QIterable<NamedValueExpression> flattenedGenericParameterList() {
    return this.membersWithGenericParameters()
      .flatten(MemberExpression::genericParameterList);
  }

  public FullyQualifiedNameExpression fqn() {
    return super.target();
  }
}
