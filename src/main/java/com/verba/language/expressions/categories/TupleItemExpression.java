package com.verba.language.expressions.categories;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.expressions.backtracking.rules.RValueExpressionBacktrackRule;
import com.verba.language.parsing.Lexer;

/**
 * Created by sircodesalot on 14-4-26.
 */
public interface TupleItemExpression {
  static BacktrackRuleSet<TupleItemExpression> rules = new BacktrackRuleSet<TupleItemExpression>()
    .addRule(new RValueExpressionBacktrackRule());


  public static TupleItemExpression read(VerbaExpression parent, Lexer lexer) {
    return rules.resolve(parent, lexer);
  }
}
