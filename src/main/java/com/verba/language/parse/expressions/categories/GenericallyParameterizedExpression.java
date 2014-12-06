package com.verba.language.parse.expressions.categories;

import com.verba.language.parse.expressions.blockheader.generic.GenericTypeListExpression;

/**
 * Created by sircodesalot on 14-5-30.
 */
public interface GenericallyParameterizedExpression extends ExpressionCategory {
  boolean hasGenericParameters();

  GenericTypeListExpression genericParameters();
}
