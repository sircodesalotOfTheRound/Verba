package com.verba.language.parsing.expressions.categories;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parsing.expressions.backtracking.rules.LiteralExpressionRule;
import com.verba.language.parsing.expressions.backtracking.rules.NamedValueExpressionBacktrackRule;
import com.verba.language.parsing.Lexer;

/**
 * Created by sircodesalot on 14-5-21.
 */
public interface MarkupRvalueExpression {
  static final BacktrackRuleSet<MarkupRvalueExpression> rules
    = new BacktrackRuleSet<MarkupRvalueExpression>()
    .addRule(new LiteralExpressionRule())
    .addRule(new NamedValueExpressionBacktrackRule());

  public static MarkupRvalueExpression read(VerbaExpression parent, Lexer lexer) {
    return rules.resolve(parent, lexer);
  }
}
