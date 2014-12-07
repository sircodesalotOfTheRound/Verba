package com.verba.language.parse.expressions.categories;

/**
 * Created by sircodesalot on 14-5-22.
 */
public interface TypedExpression extends ExpressionCategory {
  boolean hasTypeConstraint();

  TypeConstraintExpression typeConstraint();
}
