package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class RValueContainerExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return (lexer.currentIs(EnclosureToken.class, "["))
      || (lexer.currentIs(EnclosureToken.class, "("))
      || (lexer.currentIs(EnclosureToken.class, "{"));
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    if (lexer.currentIs(EnclosureToken.class, "[")) return ArrayDeclarationExpression.read(parent, lexer);
    else if (lexer.currentIs(EnclosureToken.class, "(")) return TupleDeclarationExpression.read(parent, lexer);
    else if (lexer.currentIs(EnclosureToken.class, "{")) return JsonExpression.read(parent, lexer);

    throw new NotImplementedException();
  }
}
