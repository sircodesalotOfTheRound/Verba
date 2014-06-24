package com.verba.language.expressions.categories;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;

/**
 * Created by sircodesalot on 14-5-25.
 */
public interface InvokableExpression extends TypedExpression {
    public QIterable<TupleDeclarationExpression> parameters();

    public BlockDeclarationExpression block();

}
