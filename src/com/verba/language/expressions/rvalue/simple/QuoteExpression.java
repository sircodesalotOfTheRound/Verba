package com.verba.language.expressions.rvalue.simple;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.LiteralExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexInfo;
import com.verba.language.lexing.tokens.QuoteToken;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class QuoteExpression extends VerbaExpression implements LiteralExpression {
    private final LexInfo token;

    public QuoteExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);
        this.token = lexer.readCurrentAndAdvance(QuoteToken.class);
    }

    public static QuoteExpression read(VerbaExpression parent, Lexer lexer) {
        return new QuoteExpression(parent, lexer);
    }

    public String representation() {
        return token.representation();
    }

    public LexInfo quotation() {
        return this.token;
    }
}
