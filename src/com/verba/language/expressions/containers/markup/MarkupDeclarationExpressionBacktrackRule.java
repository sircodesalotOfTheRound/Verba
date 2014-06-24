package com.verba.language.expressions.containers.markup;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRule;
import com.verba.language.expressions.backtracking.MismatchException;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexList;
import com.verba.language.lexing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-5-22.
 */
public class MarkupDeclarationExpressionBacktrackRule extends BacktrackRule {
    @Override
    public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
        return lexer.currentIs(OperatorToken.class, "<");
    }

    @Override
    public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
        return MarkupDeclarationExpression.read(parent, lexer);
    }
}
