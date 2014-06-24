package com.verba.language.expressions.containers.markup;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.MarkupRvalueExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class MarkupKeyValuePairExpression extends VerbaExpression {
    private final VerbaExpression key;
    private final MarkupRvalueExpression value;

    private MarkupKeyValuePairExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        this.key = VerbaExpression.read(this, lexer);
        lexer.readCurrentAndAdvance(OperatorToken.class, "=");
        this.value = MarkupRvalueExpression.read(this, lexer);
    }

    public static MarkupKeyValuePairExpression read(VerbaExpression parent, Lexer lexer) {
        return new MarkupKeyValuePairExpression(parent, lexer);
    }
}
