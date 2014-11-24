package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.tags.aspect.AspectTagExpression;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.tags.AspectTagToken;

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
