package com.verba.language.parse.expressions.statements.assignment;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.assignment.AssignmentToken;
import com.verba.language.parse.tokens.operators.assignment.CompositeAssignmentToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class AssignmentStatementExpression extends VerbaExpression {
  TypeConstraintExpression lvalue;
  LexInfo operation;
  RValueExpression rvalue;

  public AssignmentStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.lvalue = TypeConstraintExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, "=")
      || lexer.currentIs(CompositeAssignmentToken.class)) {

      this.operation = lexer.readCurrentAndAdvance(AssignmentToken.class);
    }

    this.rvalue = RValueExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void parse(VerbaExpression parent, Lexer lexer) {

  }

  public static AssignmentStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new AssignmentStatementExpression(parent, lexer);
  }

  public TypeConstraintExpression lvalue() {
    return this.lvalue;
  }

  public LexInfo operation() {
    return this.operation;
  }

  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }
}
