package com.verba.language.expressions.categories;

/**
 * Created by sircodesalot on 14-5-22.
 */
public interface AssignmentExpression {
    public boolean hasRValue();

    public RValueExpression rvalue();
}
