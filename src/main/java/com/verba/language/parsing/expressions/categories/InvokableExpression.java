package com.verba.language.parsing.expressions.categories;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;

/**
 * Created by sircodesalot on 14-5-25.
 */

public interface InvokableExpression extends TypedExpression {
  public QIterable<TupleDeclarationExpression> parameterSets();
  public BlockDeclarationExpression block();
}
