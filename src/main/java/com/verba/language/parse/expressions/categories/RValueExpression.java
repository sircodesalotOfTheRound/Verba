package com.verba.language.parse.expressions.categories;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parse.expressions.backtracking.rules.*;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-2-19.
 */
public interface RValueExpression extends TupleItemExpression, ExpressionCategory {
  // LambdaExpression must come before CastedRValueExpression
  // CastedRValueExpression must come before ContainerExpression!
  public final static BacktrackRuleSet<RValueExpression> ruleset
    = new BacktrackRuleSet<RValueExpression>()
    .addRule(new MarkupDeclarationExpressionBacktrackRule())
    .addRule(new BooleanExpressionBacktrackRule())
    .addRule(new MathExpressionBacktrackRule())
    .addRule(new LiteralExpressionRule())
    .addRule(new SetDeclarationExpressionBacktrackRule())
    .addRule(new NewExpressionBacktrackRule())
    .addRule(new LambdaExpressionBacktrackRule())
    .addRule(new CastedRValueExpressionBacktrackRule())
    .addRule(new NamedValueExpressionBacktrackRule());

  public static RValueExpression read(VerbaExpression parent, Lexer lexer) {
    return ruleset.resolve(parent, lexer);
  }
}
