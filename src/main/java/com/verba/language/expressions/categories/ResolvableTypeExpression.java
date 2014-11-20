package com.verba.language.expressions.categories;

import com.verba.language.graph.statictyping.SymbolTypeResolver;

/**
 * Created by sircodesalot on 14/9/12.
 */
public interface ResolvableTypeExpression {
  void accept(SymbolTypeResolver resolver);
}
