package com.verba.language.parsing.expressions.backtracking;

import com.verba.language.exceptions.ParseException;
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

  public abstract VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException;

  @Deprecated
  public <T extends VerbaExpression> T tryWithRollback(Lexer lexer, Supplier<T> expression) {
    try {
      lexer.setUndoPoint();
      T value = expression.get();
      lexer.clearUndoPoint();

      return value;

    } catch (ParseException ex) {
      lexer.rollbackToUndoPoint();
      throw MismatchException.getInstance();
    }
  }

  @Deprecated
  public boolean verifyThenRollback(Lexer lexer, Consumer<Lexer> expression) {
    try {
      lexer.setUndoPoint();
      expression.accept(lexer);
      lexer.rollbackToUndoPoint();

      return true;

    } catch (ParseException ex) {
      lexer.rollbackToUndoPoint();
      return false;
    }
  }

}
