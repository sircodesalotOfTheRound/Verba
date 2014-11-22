package com.verba.language.parsing.expressions.categories;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/22.
 */
public interface ParameterizedExpression {
  boolean hasParameters();
  QIterable<TupleDeclarationExpression> parameterSets();
}
