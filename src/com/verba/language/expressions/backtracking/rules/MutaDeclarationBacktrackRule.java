package com.verba.language.expressions.backtracking.rules;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRule;
import com.verba.language.expressions.backtracking.MismatchException;
import com.verba.language.expressions.statements.declaration.MutaDeclarationStatement;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexList;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class MutaDeclarationBacktrackRule extends BacktrackRule {
    @Override
    public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
        return restOfLine.startsWith(KeywordToken.class, "muta");
    }

    @Override
    public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
        if (restOfLine.startsWith(KeywordToken.class, "muta")) return MutaDeclarationStatement.read(parent, lexer);

        throw MismatchException.getInstance();
    }
}
