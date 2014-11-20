package com.verba.language.expressions.categories;

import com.verba.language.exceptions.ParseException;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokenization.Token;

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

    throw new ParseException("Expected Type Declaration");
  }

  public abstract String representation();

}
