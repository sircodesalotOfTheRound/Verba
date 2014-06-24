package com.verba.language.expressions.statements.returns;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.expressions.categories.StatementExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-22.
 */

public class ReturnStatementExpression extends VerbaExpression implements StatementExpression {
    private RValueExpression value;

    public ReturnStatementExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        int currentLine = lexer.current().line();
        lexer.readCurrentAndAdvance(KeywordToken.class, "ret");

        if (lexer.current().line() == currentLine) {
            this.value = RValueExpression.read(this, lexer);
        }
    }

    public static ReturnStatementExpression read(VerbaExpression expression, Lexer lexer) {
        return new ReturnStatementExpression(expression, lexer);
    }

    public RValueExpression value() {
        return this.value;
    }

    public boolean hasValue() {
        return this.value != null;
    }

}
