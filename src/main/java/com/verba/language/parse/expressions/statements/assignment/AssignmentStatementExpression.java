package com.verba.language.parse.expressions.statements.assignment;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.assignment.AssignmentToken;
import com.verba.language.parse.tokens.operators.assignment.CompositeAssignmentToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class AssignmentStatementExpression extends VerbaExpression {
  TypeDeclarationExpression lvalue;
  LexInfo operation;
  RValueExpression rvalue;

  public AssignmentStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.lvalue = TypeDeclarationExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, "=")
      || lexer.currentIs(CompositeAssignmentToken.class)) {

      this.operation = lexer.readCurrentAndAdvance(AssignmentToken.class);
    }

    this.rvalue = RValueExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static AssignmentStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new AssignmentStatementExpression(parent, lexer);
  }

  public TypeDeclarationExpression lvalue() {
    return this.lvalue;
  }

  public LexInfo operation() {
    return this.operation;
  }

  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }
}
