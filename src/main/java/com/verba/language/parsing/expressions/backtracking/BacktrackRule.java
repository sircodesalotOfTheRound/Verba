package com.verba.language.parsing.expressions.backtracking;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexList;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by sircodesalot on 14-2-20.
 */
public abstract class BacktrackRule {
  public abstract boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine);

  public abstract VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine);
}
