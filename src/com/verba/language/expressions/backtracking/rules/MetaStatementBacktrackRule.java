package com.verba.language.expressions.backtracking.rules;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRule;
import com.verba.language.expressions.backtracking.MismatchException;
import com.verba.language.expressions.statements.meta.MetaStatementExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexList;
import com.verba.language.lexing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-2-23.
 */
public class MetaStatementBacktrackRule extends BacktrackRule {
    @Override
    public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
        return restOfLine.startsWith(OperatorToken.class, "@");
    }

    @Override
    public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
        return tryWithRollback(lexer, () -> MetaStatementExpression.read(parent, lexer));
    }
}
