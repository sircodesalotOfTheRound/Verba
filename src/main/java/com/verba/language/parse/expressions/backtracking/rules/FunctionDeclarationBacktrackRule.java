package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-22.
 */
public class FunctionDeclarationBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    // If the current token is 'fn', then yeah it's a function.
    if (restOfLine.startsWith(KeywordToken.class, KeywordToken.FN)) {
      return true;
    }

    return isConstructorSignature(parent, lexer);
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return FunctionDeclarationExpression.read(parent, lexer);
  }

  public static boolean isConstructorSignature(VerbaExpression parent, Lexer lexer) {
    PolymorphicDeclarationExpression declaringType = determineIfPartOfClass(parent);
    String currentToken = lexer.current().representation();

    return (declaringType != null && currentToken.equals(declaringType.name()));
  }

  public static PolymorphicDeclarationExpression determineIfPartOfClass(VerbaExpression expression) {
    if (expression == null) {
      return null;
    }

    // If the 'immediate-ish' parent is a class, then return that class.
    // If the parent is a function, then return nothing (can't be a construct.
    if (expression.is(PolymorphicDeclarationExpression.class)) {
      return (PolymorphicDeclarationExpression) expression;
    } else if (expression.is(FunctionDeclarationExpression.class)) {
      return null;
    } else {
        return determineIfPartOfClass(expression.parent());
    }
  }
}
