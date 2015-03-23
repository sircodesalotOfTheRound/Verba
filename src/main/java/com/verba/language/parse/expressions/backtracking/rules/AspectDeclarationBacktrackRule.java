package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.metatags.aspect.AspectTagExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.tags.AspectTagToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class AspectDeclarationBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return restOfLine.startsWith(AspectTagToken.class);
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return AspectTagExpression.read(parent, lexer);
  }
}
