package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.immediate.ImmediateFunctionExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 15/2/26.
 */
public class ImmediateFunctionExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    if (!restOfLine.first().is(EnclosureToken.class, EnclosureToken.OPEN_PARENS)) {
      return false;
    }

    LexInfo secondItem = restOfLine.second();
    return (secondItem.is(KeywordToken.FN) || secondItem.is(KeywordToken.ASYNC));
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return ImmediateFunctionExpression.read(parent, lexer);
  }
}
