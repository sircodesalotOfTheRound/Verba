package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parse.expressions.rvalue.lambda.LambdaExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.IdentifierToken;
import com.verba.language.parse.tokens.lambda.LambdaToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-28.
 */
public class LambdaExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    boolean isLambdaExpression = false;
    lexer.setUndoPoint();

    if (lexer.notEOF() && (lexer.currentIs(EnclosureToken.class, "(") || lexer.currentIs(IdentifierToken.class))) {
        TypeDeclarationExpression.read(parent, lexer);
    }

    if (lexer.notEOF() && (lexer.currentIs(LambdaToken.class))) {
      isLambdaExpression = true;
    }

    lexer.rollbackToUndoPoint();

    return isLambdaExpression;
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return LambdaExpression.read(parent, lexer);
  }
}
