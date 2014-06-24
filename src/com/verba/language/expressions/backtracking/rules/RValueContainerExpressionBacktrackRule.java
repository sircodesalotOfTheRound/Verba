package com.verba.language.expressions.backtracking.rules;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRule;
import com.verba.language.expressions.backtracking.MismatchException;
import com.verba.language.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.expressions.containers.json.JsonExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexList;
import com.verba.language.lexing.tokens.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class RValueContainerExpressionBacktrackRule extends BacktrackRule {
    @Override
    public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
        return (lexer.currentIs(EnclosureToken.class, "["))
            || (lexer.currentIs(EnclosureToken.class, "("))
            || (lexer.currentIs(EnclosureToken.class, "{"));
    }

    @Override
    public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
        if (lexer.currentIs(EnclosureToken.class, "[")) return ArrayDeclarationExpression.read(parent, lexer);
        else if (lexer.currentIs(EnclosureToken.class, "(")) return TupleDeclarationExpression.read(parent, lexer);
        else if (lexer.currentIs(EnclosureToken.class, "{")) return JsonExpression.read(parent, lexer);

        throw MismatchException.getInstance();
    }
}
