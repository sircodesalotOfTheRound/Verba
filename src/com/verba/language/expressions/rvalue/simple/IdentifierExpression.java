package com.verba.language.expressions.rvalue.simple;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexInfo;
import com.verba.language.lexing.tokens.identifiers.IdentifierToken;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class IdentifierExpression extends VerbaExpression implements RValueExpression {
    private LexInfo identifier;

    public IdentifierExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        this.identifier = lexer.readCurrentAndAdvance(IdentifierToken.class);
    }

    public static IdentifierExpression read(VerbaExpression parent, Lexer lexer) {
        return new IdentifierExpression(parent, lexer);
    }

    public LexInfo identifier() {
        return this.identifier;
    }

    public String representation() {
        return this.identifier.representation();
    }
}

