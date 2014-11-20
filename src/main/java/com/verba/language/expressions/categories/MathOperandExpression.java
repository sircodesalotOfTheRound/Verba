package com.verba.language.expressions.categories;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.expressions.backtracking.rules.MathExpressionBacktrackRule;
import com.verba.language.parsing.Lexer;

/**
 * Created by sircodesalot on 14/9/18.
 */
public interface MathOperandExpression {
  public static BacktrackRuleSet<MathOperandExpression> ruleset = new BacktrackRuleSet<>(RValueExpression.ruleset
    .where(rule -> !(rule instanceof MathExpressionBacktrackRule)));

  public static MathOperandExpression read(VerbaExpression parent, Lexer lexer) {
    return ruleset.resolve(parent, lexer);
  }
}
