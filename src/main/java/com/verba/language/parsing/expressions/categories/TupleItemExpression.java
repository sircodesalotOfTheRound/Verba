package com.verba.language.parsing.expressions.categories;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parsing.expressions.backtracking.rules.RValueExpressionBacktrackRule;
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
