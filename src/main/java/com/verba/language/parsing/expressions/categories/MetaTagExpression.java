package com.verba.language.parsing.expressions.categories;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parsing.expressions.backtracking.rules.AspectDeclarationBacktrackRule;
import com.verba.language.parsing.expressions.backtracking.rules.HashtagDeclarationBacktrackRule;
import com.verba.language.parsing.Lexer;

/**
 * Created by sircodesalot on 14-4-15.
 */
public interface MetaTagExpression {
  public static BacktrackRuleSet<MetaTagExpression> metatags
    = new BacktrackRuleSet<MetaTagExpression>()
    .addRule(new HashtagDeclarationBacktrackRule())
    .addRule(new AspectDeclarationBacktrackRule());

  public static MetaTagExpression read(VerbaExpression expression, Lexer lexer) {
    return metatags.resolve(expression, lexer);
  }
}
