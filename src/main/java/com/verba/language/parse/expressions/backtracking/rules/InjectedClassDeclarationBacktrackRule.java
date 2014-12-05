package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.blockheader.classes.InjectedDeclarationExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-23.
 */

// This class doesn't make sense since the new declaration should be:
//    inject class MyClass
// not:
//    injected class MyClass
//
@Deprecated
public class InjectedClassDeclarationBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return restOfLine.startsWith(KeywordToken.class, KeywordToken.INJECT);
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
      return InjectedDeclarationExpression.read(parent, lexer);
  }
}
