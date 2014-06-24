package com.verba.language.expressions.blockheader.sql;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class SqlExpression extends VerbaExpression {
    private final FullyQualifiedNameExpression identifier;

    private SqlExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        lexer.readNext(KeywordToken.class, "sql");
        this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    }

    public static VerbaExpression read(VerbaExpression parent, Lexer lexer) {
        return new SqlExpression(parent, lexer);
    }

    public FullyQualifiedNameExpression identifier() {
        return this.identifier;
    }

}
