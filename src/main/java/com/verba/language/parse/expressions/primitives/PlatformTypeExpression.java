package com.verba.language.parse.expressions.primitives;

import com.javalinq.implementations.QSet;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.platform.PlatformSourceExpression;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class PlatformTypeExpression extends VerbaExpression {
  public static final PlatformTypeExpression UNIT = new PlatformTypeExpression(KeywordToken.UNIT);
  public static final PlatformTypeExpression UTF = new PlatformTypeExpression(KeywordToken.UTF);
  public static final PlatformTypeExpression ASCII = new PlatformTypeExpression(KeywordToken.ASCII);
  public static final PlatformTypeExpression INT = new PlatformTypeExpression(KeywordToken.INT);
  public static final PlatformTypeExpression DYNAMIC = new PlatformTypeExpression(KeywordToken.DYNAMIC);
  public static final PlatformTypeExpression BOOLEAN = new PlatformTypeExpression(KeywordToken.BOOLEAN);


  private final QSet<String> validPrimitiveTypes = new QSet<>(KeywordToken.platformTypeKeywords());
  private final String type;

  public PlatformTypeExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.type = null;
  }

  public PlatformTypeExpression(String type) {
    super(PlatformSourceExpression.INSTANCE, null);
    this.type = validateType(type);
  }

  private String validateType(String type) {
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
