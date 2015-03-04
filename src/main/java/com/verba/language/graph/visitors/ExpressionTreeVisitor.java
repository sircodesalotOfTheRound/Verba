package com.verba.language.graph.visitors;

import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.TupleItemExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpressionPair;
import com.verba.language.parse.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.immediate.ImmediateFunctionExpression;
import com.verba.language.parse.expressions.interop.AsmBlockExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parse.expressions.rvalue.simple.BooleanExpression;
import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/9/12.
 */
public abstract class ExpressionTreeVisitor {
  public void onNodeVisited(VerbaExpression expression) { }

  public void visitAll(Iterable<VerbaExpression> expressions) {
    for (VerbaExpression expression : expressions) {
      expression.accept(this);
    }
  }

  public void visit(BlockDeclarationExpression block) {
    for (VerbaExpression expression : block.expressions()) {
      expression.accept(this);
    }
  }

  public void visit(LitFileRootExpression litFileRoot) {
    for (VerbaCodePage page : litFileRoot.pages()) {
      page.accept(this);
    }
  }

  public void visit(NamedValueExpression namedExpression) {
    namedExpression.identifier().accept(this);

    if (namedExpression.hasTypeConstraint()) {
      VerbaExpression typeConstraintExpression = (VerbaExpression) namedExpression.typeConstraint();
      typeConstraintExpression.accept(this);
    }
  }

  public void visit(PolymorphicDeclarationExpression declaration) {
    declaration.declaration().accept(this);
    declaration.block().accept(this);
  }

  public void visit(FunctionDeclarationExpression function) {
    function.declaration().accept(this);
    function.block().accept(this);
  }


  public void visit(ArrayDeclarationExpression array) {
    for (VerbaExpression expression : array.items()) {
      expression.accept(this);
    }
  }

  public void visit(JsonExpression json) {
    for (JsonExpressionPair pair : json.items()) {
      VerbaExpression lhs = pair.lhs();
      VerbaExpression rhs = (VerbaExpression)pair.rhs();

      lhs.accept(this);
      rhs.accept(this);
    }
  }

  public void visit(TupleDeclarationExpression expression) {
    for (TupleItemExpression item : expression.items()) {
      VerbaExpression verbaExpression = (VerbaExpression)item;
      verbaExpression.accept(this);
    }
  }

  public void visit(VerbaCodePage page) {
    for (VerbaExpression expression : page.childExpressions()) {
      expression.accept(this);
    }
  }

  public void visit(ReturnStatementExpression statement) {
    if (statement.hasValue()) {
      VerbaExpression expression = (VerbaExpression) statement.value();
      expression.accept(this);
    }
  }

  public void visit(QuoteExpression quotation) {
    // TODO: This should allow for parsing information out of the quotes.
  }

  public void visit(AssignmentStatementExpression assignment) {
    VerbaExpression lhs = (VerbaExpression)assignment.lvalue();
    VerbaExpression rhs = (VerbaExpression)assignment.rvalue();

    lhs.accept(this);
    rhs.accept(this);
  }

  public void visit(NumericExpression expression) {

  }

  public void visit(ValDeclarationStatement statement) {
    statement.identifier().accept(this);

    if (statement.hasTypeConstraint()) {
      VerbaExpression typeConstraint = (VerbaExpression) statement.typeConstraint();
      typeConstraint.accept(this);
    }

    if (statement.hasRValue()) {
      VerbaExpression rvalue = (VerbaExpression) statement.rvalue();
      rvalue.accept(this);
    }
  }

  public void visit(WithNsExpression expression) {
    expression.namespace().accept(this);
  }

  public void visit(MarkupDeclarationExpression markup) {
    this.visitAll(markup.items().cast(VerbaExpression.class));
  }

  public void visit(DeclarationModifierExrpression modifier) {
    modifier.modifiedExpression().accept(this);
  }

  public void visit(NewExpression expression) {
    VerbaExpression verbaExpression = expression.identifier();
    verbaExpression.accept(this);
  }

  public void visit(BooleanExpression expression) {

  }

  public void visit(InfixExpression expression) {
    if (expression.hasLhs()) {
      expression.lhs().accept(this);
    }

    expression.rhs().accept(this);
  }

  public void visit(ImmediateFunctionExpression immediateFunctionExpression) {
    this.visit(immediateFunctionExpression.function());
    this.visitAll(immediateFunctionExpression.arguments().items().cast(VerbaExpression.class));
  }

  public void visit(AsmBlockExpression asm) {

  }
}
