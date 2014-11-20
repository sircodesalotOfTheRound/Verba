package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.backtracking.MismatchException;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.identifiers.IdentifierToken;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-4-26.
 */
public class FullyQualifiedNameExpressionBacktrackRule extends BacktrackRule {

  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return lexer.currentIs(IdentifierToken.class) && !lexer.currentIs(KeywordToken.class);
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
    return FullyQualifiedNameExpression.read(parent, lexer);
  }
}
