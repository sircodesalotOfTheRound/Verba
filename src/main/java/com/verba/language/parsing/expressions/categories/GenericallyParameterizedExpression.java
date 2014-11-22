package com.verba.language.parsing.expressions.categories;

import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;

/**
 * Created by sircodesalot on 14-5-30.
 */
public interface GenericallyParameterizedExpression {
  boolean hasGenericParameters();

  GenericTypeListExpression genericParameters();
}
