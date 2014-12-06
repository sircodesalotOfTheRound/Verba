package com.verba.language.parse.expressions.categories;

/**
 * Created by sircodesalot on 14-5-22.
 */
public interface AssignmentExpression extends ExpressionCategory {
  public boolean hasRValue();

  public RValueExpression rvalue();
}
