package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class DeclarationModifierBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    if (lexer.currentIs(KeywordToken.class)) {
      String currentToken = lexer.current().representation();
      return KeywordToken.isAccessModifierKeyword(currentToken);
    }

    return false;
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return DeclarationModifierExrpression.read(parent, lexer);
  }
}
