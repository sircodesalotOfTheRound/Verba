package com.verba.language.parse.expressions.categories;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parse.expressions.backtracking.rules.LiteralExpressionRule;
import com.verba.language.parse.expressions.backtracking.rules.NamedValueExpressionBacktrackRule;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-5-21.
 */
public interface MarkupRvalueExpression extends ExpressionCategory {
  static final BacktrackRuleSet<MarkupRvalueExpression> rules
    = new BacktrackRuleSet<MarkupRvalueExpression>()
    .addRule(new LiteralExpressionRule())
    .addRule(new NamedValueExpressionBacktrackRule());

  public static MarkupRvalueExpression read(VerbaExpression parent, Lexer lexer) {
    return rules.resolve(parent, lexer);
  }
}
