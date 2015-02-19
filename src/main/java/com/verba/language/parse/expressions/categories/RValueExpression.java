package com.verba.language.parse.expressions.categories;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parse.expressions.backtracking.rules.*;
import com.verba.language.parse.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-19.
 */
public interface RValueExpression extends TupleItemExpression, ExpressionCategory {
  // LambdaExpression must come before CastedRValueExpression
  // CastedRValueExpression must come before ContainerExpression!
  public final static BacktrackRuleSet<RValueExpression> rvalueRuleSet
    = new BacktrackRuleSet<RValueExpression>()
    .addRule(new MarkupDeclarationExpressionBacktrackRule())
    .addRule(new BooleanExpressionBacktrackRule())
    .addRule(new LambdaExpressionBacktrackRule())
    .addRule(new MathExpressionBacktrackRule())
    .addRule(new LiteralExpressionRule())
    .addRule(new SetDeclarationExpressionBacktrackRule())
    .addRule(new NewExpressionBacktrackRule())
    .addRule(new CastedRValueExpressionBacktrackRule())
    .addRule(new NamedValueExpressionBacktrackRule());

  public static RValueExpression readNonMathExpression(VerbaExpression parent, Lexer lexer) {
    return rvalueRuleSet.resolve(parent, lexer);
  }

  public static RValueExpression read(VerbaExpression parent, Lexer lexer) {
    lexer.setUndoPoint();
    RValueExpression expression = rvalueRuleSet.resolve(parent, lexer);

    // If the next item is a mathematical operator, reread the current token, but
    // wrap it in a MathOpExpression.
    if (lexer.currentIs(MathOpToken.class)) {
      lexer.rollbackToUndoPoint();
      return (RValueExpression)MathOpExpression.read(parent, lexer);
    }

    lexer.clearUndoPoint();
    return expression;
  }
}
