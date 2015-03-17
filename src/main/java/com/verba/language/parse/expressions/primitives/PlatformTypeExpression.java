package com.verba.language.parse.expressions.primitives;

import com.javalinq.implementations.QSet;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class PlatformTypeExpression extends VerbaExpression {
  private final QSet<String> validPrimitiveTypes = new QSet<>(KeywordToken.platformTypeKeywords());
  private final String type;

  public PlatformTypeExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.type = null;
  }

  public PlatformTypeExpression(String type) {
    super(null, null);
    this.type = validateType(type);
  }

  public String validateType(String type) {
    if (validPrimitiveTypes.contains(type)) {
      return type;
    } else {
      throw new CompilerException("Invalid primitive type name");
    }
  }

  public String type() { return this.type; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
