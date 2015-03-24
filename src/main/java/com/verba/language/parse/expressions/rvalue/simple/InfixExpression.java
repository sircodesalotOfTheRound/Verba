package com.verba.language.parse.expressions.rvalue.simple;

import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.tools.InfixOperatorPrecedenceComparator;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.InfixOperatorToken;


/**
 * Created by sircodesalot on 14-2-27.
 */
public class InfixExpression extends VerbaExpression implements RValueExpression {
  private VerbaExpression lhs;
  private VerbaExpression rhs;
  private final LexInfo operationToken;

  public InfixExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    // This checks for unary expressions (vs. binary expressions)
    if (!lexer.currentIs(InfixOperatorToken.class)) {
      this.lhs = (VerbaExpression)RValueExpression.readIgnoreInfixExpressions(parent, lexer);
    }

    this.operationToken = lexer.readCurrentAndAdvance(InfixOperatorToken.class);
    this.rhs = readRhs(parent, lexer);
    this.closeLexingRegion();
  }

  // In some situations, RHS could be a mathematical sub tree.
  // For example: 4 + 5 + 6 needs to be parsed as:
  //    +
  //   / \
  //  4  +
  //    / \
  //   5  6
  // So we need to process when that happens.
  public VerbaExpression readRhs(VerbaExpression parent, Lexer lexer) {
    lexer.setUndoPoint();

    RValueExpression expression = RValueExpression.readIgnoreInfixExpressions(parent, lexer);

    // If reading the last expression ended in a math operator, then
    if (lexer.currentIs(InfixOperatorToken.class)) {
      lexer.rollbackToUndoPoint();
      return InfixExpression.read(this, lexer);
    } else {
      lexer.clearUndoPoint();
      return (VerbaExpression) expression;
    }
  }

  // Re-structure the tree to reflect proper operator precedence.
  public static InfixExpression updateOperatorPrecedence(InfixExpression expression) {
    if (expression.lhs() instanceof InfixExpression) {
      updateOperatorPrecedence((InfixExpression) expression.lhs);
    }

    if (expression.rhs() instanceof InfixExpression) {
      updateOperatorPrecedence((InfixExpression) expression.rhs);
    }

    if (expression.hasHigherPrecedenceThan(expression.lhs)) {
      expression = rotateLeft(expression);
    }

    if (expression.hasHigherPrecedenceThan(expression.rhs)) {
      expression = rotateRight(expression);
    }

    return expression;
  }

  // Swap parents on these mathematical nodes.
  public static InfixExpression rotateLeft(InfixExpression expression) {
    InfixExpression newParent = (InfixExpression)expression.lhs;
    expression.lhs = newParent.rhs;

    if (newParent.rhs != null) {
      newParent.rhs.setParent(expression);
    }

    newParent.rhs = expression;
    expression.setParent(newParent);

    return newParent;
  }

  // Swap parents on these mathematical nodes.
  public static InfixExpression rotateRight(InfixExpression expression) {
    InfixExpression newParent = (InfixExpression)expression.rhs;
    expression.rhs = newParent.lhs;

    if (newParent.lhs != null) {
      newParent.lhs.setParent(expression);
    }

    newParent.lhs = expression;
    expression.setParent(newParent);

    return newParent;
  }

  public boolean hasLhs() { return this.lhs == null; }
  public VerbaExpression lhs() { return this.lhs; }
  public VerbaExpression rhs() { return this.rhs; }

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

  @Override
  public void onValidate(Build build, SymbolTable table) {

  }

  public boolean hasHigherPrecedenceThan(VerbaExpression rhs) {
    if (rhs != null && rhs instanceof InfixExpression) {
      return InfixOperatorPrecedenceComparator.comparePrecedence(this, (InfixExpression)rhs) < 0;
    } else {
      return false;
    }
  }

  public LexInfo operator() { return this.operationToken; }

  public static InfixExpression read(VerbaExpression parent, Lexer lexer) {
    InfixExpression expression = new InfixExpression(parent, lexer);
    return updateOperatorPrecedence(expression);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return visitor.visit(this);
  }
}
