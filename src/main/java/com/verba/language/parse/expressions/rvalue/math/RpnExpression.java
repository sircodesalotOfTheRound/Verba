package com.verba.language.parse.expressions.rvalue.math;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.InfixOperatorToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
@Deprecated
public class RpnExpression extends VerbaExpression implements RValueExpression {
  // Should probabaly be a tree rather than a list.
  private final RpnMap expressions;

  private RpnExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.expressions = new RpnMap(parent, lexer);
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

  private boolean isNextMathToken(Lexer lexer) {
    boolean isIt = false;

    lexer.setUndoPoint();
    lexer.advance();
    if (lexer.notEOF() && lexer.currentIs(InfixOperatorToken.class)) {
      isIt = true;
    }
    lexer.rollbackToUndoPoint();

    return isIt;
  }

  public static RpnExpression read(VerbaExpression parent, Lexer lexer) {
    return new RpnExpression(parent, lexer);
  }

  public RpnMap expressions() {
    return this.expressions;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
