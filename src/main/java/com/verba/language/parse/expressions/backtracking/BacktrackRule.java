package com.verba.language.parse.expressions.backtracking;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-2-20.
 */
public abstract class BacktrackRule {
  public abstract boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine);

  public abstract VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine);
}
