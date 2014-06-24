package com.verba.language.expressions.backtracking.rules;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRule;
import com.verba.language.expressions.backtracking.MismatchException;
import com.verba.language.expressions.tags.hashtag.HashTagExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexList;
import com.verba.language.lexing.tokens.operators.tags.HashTagToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class HashtagDeclarationBacktrackRule extends BacktrackRule {
    @Override
    public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
        return restOfLine.startsWith(HashTagToken.class);
    }

    @Override
    public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
        return tryWithRollback(lexer, () -> HashTagExpression.read(parent, lexer));
    }
}
