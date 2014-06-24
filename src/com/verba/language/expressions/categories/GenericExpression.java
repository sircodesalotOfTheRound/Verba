package com.verba.language.expressions.categories;

import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;

/**
 * Created by sircodesalot on 14-5-30.
 */
public interface GenericExpression {
    boolean hasGenericParameters();

    GenericTypeListExpression genericParameters();
}
