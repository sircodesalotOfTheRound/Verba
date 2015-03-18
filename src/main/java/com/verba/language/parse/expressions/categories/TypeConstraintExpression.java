package com.verba.language.parse.expressions.categories;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.platform.expressions.PlatformTypeExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-25.
 */
public interface TypeConstraintExpression extends ExpressionCategory {
  public static TypeConstraintExpression read(VerbaExpression parent, Lexer lexer) {
    // TODO: Determine if this section is still in use.
    if (PlatformTypeExpression.isPlatformTypeName(lexer)) {
      return PlatformTypeExpression.read(parent, lexer);
    } else if (FullyQualifiedNameExpression.isFullyQualifiedName(lexer)) {
      return FullyQualifiedNameExpression.read(parent, lexer);
    } else if (TupleDeclarationExpression.isTupleTypeDeclaration(lexer)) {
      return TupleDeclarationExpression.read(parent, lexer);
    }

    throw new NotImplementedException();
  }

  public abstract String representation();

}
