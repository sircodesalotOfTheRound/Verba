package com.verba.language.parse.expressions.categories;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/22.
 */
public interface ParameterizedExpression extends ExpressionCategory {
  boolean hasParameters();
  QIterable<TupleDeclarationExpression> parameterSets();
}
