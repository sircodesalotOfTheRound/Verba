package com.verba.language.parsing.expressions.statements.assignment;

import com.verba.language.graph.analysis.expressions.tools.BuildProfileBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;
import com.verba.language.parsing.tokens.operators.assignment.AssignmentToken;
import com.verba.language.parsing.tokens.operators.assignment.CompositeAssignmentToken;

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

  @Override
  public BuildProfileBase buildProfile() {
    return null;
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
