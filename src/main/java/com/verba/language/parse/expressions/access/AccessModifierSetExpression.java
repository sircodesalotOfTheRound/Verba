package com.verba.language.parse.expressions.access;

import com.javalinq.implementations.QList;
import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-5-20.
 */
public class AccessModifierSetExpression extends VerbaExpression {
  private final QList<AccessModifierExpression> expressions = new QList<>();

  public AccessModifierSetExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    while (lexer.notEOF() && AccessModifierExpression.ACCESS_MODIFIERS.contains(lexer.current().representation())) {
      AccessModifierExpression accessModifier = AccessModifierExpression.read(this, lexer);
      this.expressions.add(accessModifier);
    }

    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void afterContentsParsed(Build build) {

  }

  @Override
  public void afterSymbolsGenerated(Build build, SymbolTable table) {

  }

  @Override
  public void onResolveSymbols(Build build, SymbolTable table) {

  }

  public static AccessModifierSetExpression read(VerbaExpression parent, Lexer lexer) {
    return new AccessModifierSetExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
