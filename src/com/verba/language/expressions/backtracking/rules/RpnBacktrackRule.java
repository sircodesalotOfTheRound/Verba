package com.verba.language.expressions.backtracking.rules;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRule;
import com.verba.language.expressions.backtracking.MismatchException;
import com.verba.language.expressions.rvalue.pemdas.RpnRValueExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexList;
import com.verba.language.lexing.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class RpnBacktrackRule extends BacktrackRule {
    @Override
    public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
        // This will require shunting if the second item is a math token.
        return (restOfLine.length() > 2) && (restOfLine.get(1).is(MathOpToken.class));
    }

    @Override
    public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
        return tryWithRollback(lexer, () -> RpnRValueExpression.read(parent, lexer));
    }
}
