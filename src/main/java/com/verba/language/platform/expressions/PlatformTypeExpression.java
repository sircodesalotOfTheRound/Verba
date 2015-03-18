package com.verba.language.platform.expressions;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class PlatformTypeExpression extends VerbaExpression implements TypeConstraintExpression {
  public static final PlatformTypeExpression UNIT = new PlatformTypeExpression(KeywordToken.UNIT);
  public static final PlatformTypeExpression UTF = new PlatformTypeExpression(KeywordToken.UTF);
  public static final PlatformTypeExpression ASCII = new PlatformTypeExpression(KeywordToken.ASCII);
  public static final PlatformTypeExpression INT = new PlatformTypeExpression(KeywordToken.INT);
  public static final PlatformTypeExpression DYNAMIC = new PlatformTypeExpression(KeywordToken.DYNAMIC);
  public static final PlatformTypeExpression BOOLEAN = new PlatformTypeExpression(KeywordToken.BOOLEAN);

  private static final QSet<PlatformTypeExpression> validPrimitiveTypes = new QSet<>(
    UNIT, UTF, ASCII, INT, DYNAMIC, BOOLEAN
  );

  private static final Partition<String, PlatformTypeExpression> expressionsByName
    = validPrimitiveTypes.parition(PlatformTypeExpression::type);

  private final String type;

  public PlatformTypeExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);
    this.type = validateType(lexer);
  }

  private PlatformTypeExpression(String type) {
    super(PlatformSourceExpression.INSTANCE, null);
    this.type = type;
  }


  private String validateType(Lexer lexer) {
    if (isPlatformTypeName(lexer)) {
      return lexer.readCurrentAndAdvance().representation();
    } else {
      throw new CompilerException("Invalid Platform Type");
    }
  }

  public String type() { return this.type; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }

  public static boolean isPlatformTypeName(Lexer lexer) {
    if (lexer.isEOF()) {
      return false;
    }

    String representation = lexer.current().representation();
    return expressionsByName.containsKey(representation);
  }

  public static PlatformTypeExpression read(VerbaExpression parent, Lexer lexer) {
    return new PlatformTypeExpression(parent, lexer);
  }

  @Override
  public String representation() { return this.type; }
}
