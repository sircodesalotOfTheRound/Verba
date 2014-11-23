package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.rvalue.lambda.LambdaExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parsing.tokens.lambda.LambdaToken;
import com.verba.language.parsing.tokens.identifiers.IdentifierToken;

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
