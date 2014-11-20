package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.exceptions.ParseException;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.backtracking.MismatchException;
import com.verba.language.parsing.expressions.rvalue.cast.CastedRValueExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class CastedRValueExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    if (!restOfLine.startsWith(EnclosureToken.class, "(")) return false;

    try {
      lexer.setUndoPoint();
      CastedRValueExpression.read(parent, lexer);
      lexer.rollbackToUndoPoint();
      return true;

    } catch (ParseException ex) {
      lexer.rollbackToUndoPoint();
      return false;
    }
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
    return CastedRValueExpression.read(parent, lexer);
  }
}
