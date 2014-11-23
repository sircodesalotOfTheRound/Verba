package com.verba.language.parsing.expressions.categories;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokenization.Token;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-25.
 */
public interface TypeDeclarationExpression extends Token {


  public static TypeDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    if (FullyQualifiedNameExpression.IsFullyQualifiedName(lexer)) {
      return FullyQualifiedNameExpression.read(parent, lexer);
    } else if (TupleDeclarationExpression.isTupleTypeDeclaration(lexer)) {
      return TupleDeclarationExpression.read(parent, lexer);
    }

    throw new NotImplementedException();
  }

  public abstract String representation();

}
