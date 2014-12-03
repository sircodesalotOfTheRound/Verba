package com.verba.language.parse.expressions.categories;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Represents some sort of non-polymorphic container object (Array, Tuple, Json, Set).
 */
public interface DataContainerExpression {

  public QIterable<VerbaExpression> items();

  public static DataContainerExpression read(VerbaExpression parent, Lexer lexer) {
    if (lexer.currentIs(EnclosureToken.class, "[")) return ArrayDeclarationExpression.read(parent, lexer);
    else if (lexer.currentIs(EnclosureToken.class, "(")) return TupleDeclarationExpression.read(parent, lexer);
    else if (lexer.currentIs(EnclosureToken.class, "{")) return JsonExpression.read(parent, lexer);

    throw new NotImplementedException();
  }
}
