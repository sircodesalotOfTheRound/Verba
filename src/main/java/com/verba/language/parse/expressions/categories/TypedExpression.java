package com.verba.language.parse.expressions.categories;

import com.verba.language.graph.symbols.table.entries.Symbol;

/**
 * Created by sircodesalot on 14-5-22.
 */
public interface TypedExpression extends ExpressionCategory {

  boolean hasTypeConstraint();
  TypeConstraintExpression typeConstraint();
  Symbol resolvedType();
}
