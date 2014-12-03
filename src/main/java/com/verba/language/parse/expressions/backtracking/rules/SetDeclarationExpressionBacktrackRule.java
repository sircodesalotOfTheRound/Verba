package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.containers.set.SetDeclarationExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class SetDeclarationExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    lexer.setUndoPoint();

    boolean shouldAttempt = firstItemIsOpenBrace(lexer)
      && secondItemIsRvalueExpression(lexer)
      && thirdItemIsCommaOrBrace(lexer);

    lexer.rollbackToUndoPoint();
    return shouldAttempt;
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return SetDeclarationExpression.read(parent, lexer);
  }

  private boolean firstItemIsOpenBrace(Lexer lexer) {
    if (lexer.notEOF() && lexer.currentIs(EnclosureToken.class, "{")) {
      lexer.readCurrentAndAdvance(EnclosureToken.class, "{");
      return true;
    }

    return false;
  }

  private boolean secondItemIsRvalueExpression(Lexer lexer) {
    return (lexer.notEOF() && VerbaExpression.read(null, lexer) instanceof RValueExpression);
  }

  private boolean thirdItemIsCommaOrBrace(Lexer lexer) {
    return (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ",") || lexer.currentIs(EnclosureToken.class, "}"));
  }
}
