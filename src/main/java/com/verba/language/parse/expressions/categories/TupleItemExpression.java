package com.verba.language.parse.expressions.categories;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parse.expressions.backtracking.rules.RValueExpressionBacktrackRule;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-4-26.
 */
public interface TupleItemExpression extends ExpressionCategory {
  static BacktrackRuleSet<TupleItemExpression> rules = new BacktrackRuleSet<TupleItemExpression>()
    .addRule(new RValueExpressionBacktrackRule());


  public static TupleItemExpression read(VerbaExpression parent, Lexer lexer) {
    return rules.resolve(parent, lexer);
  }
}
