package com.verba.language.parse.expressions.immediate;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 15/2/26.
 */
public class ImmediateFunctionExpression extends VerbaExpression {
  private final FunctionDeclarationExpression function;
  private final TupleDeclarationExpression arguments;

  public ImmediateFunctionExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.function = readFunctionDeclaration(lexer);
    this.arguments = readArgumentsList(lexer);
  }

  private TupleDeclarationExpression readArgumentsList(Lexer lexer) {
    return TupleDeclarationExpression.read(this, lexer);
  }

  private FunctionDeclarationExpression readFunctionDeclaration(Lexer lexer) {
    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
    FunctionDeclarationExpression function = FunctionDeclarationExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

    return function;
  }

  public FunctionDeclarationExpression function() { return this.function; }
  public TupleDeclarationExpression arguments() { return this.arguments; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  public static ImmediateFunctionExpression read(VerbaExpression parent, Lexer lexer) {
    return new ImmediateFunctionExpression(parent, lexer);
  }
}
