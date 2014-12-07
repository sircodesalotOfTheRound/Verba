package com.verba.language.graph.tools;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.expressions.withns.WithNsExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Flattens a tree of expressions into a QList.
 */
public class SyntaxTreeFlattener implements SyntaxGraphVisitor, Serializable, QIterable<VerbaExpression> {
  private final QList<VerbaExpression> expressions = new QList<>();

  public SyntaxTreeFlattener(VerbaExpression root) {
    root.accept(this);
  }

  public QIterable<VerbaExpression> expressions() {
    return this.expressions;
  }

  @Override
  public Iterator<VerbaExpression> iterator() {
    return expressions.iterator();
  }

  private void add(VerbaExpression expression) {
    this.expressions.add(expression);
  }

  public void visit(StaticSpaceExpression node) {
    add(node);
    this.visitAll(node.pages());
  }

  public void visit(NamedValueExpression node) {
    add(node);

    if (node.hasTypeConstraint()) {
      VerbaExpression expression = (VerbaExpression)node.typeConstraint();
      expression.accept(this);
    }
  }

  // Todo: Should this also read statements as well? Probabaly?

  public void visit(PolymorphicDeclarationExpression classDeclaration) {
    add(classDeclaration);

    this.visitAll(classDeclaration.block());
  }

  public void visit(FunctionDeclarationExpression function) {
    add(function);

    this.visitAll(function.parameterSets().flatten(TupleDeclarationExpression::items));
    this.visitAll(function.block());
  }

  public void visit(ArrayDeclarationExpression array) {
    add(array);
    this.visitAll(array.items());
  }

  public void visit(JsonExpression jsonExpression) {
    add(jsonExpression);
    visitAll(jsonExpression.items());
  }

  public void visit(TupleDeclarationExpression tuple) {
    add(tuple);
    this.visitAll(tuple.items());
  }

  public void visit(BlockDeclarationExpression block) {
    add(block);
    this.visitAll(block);
  }

  public void visit(VerbaCodePage page) {
    add(page);
    this.visitAll(page.allExpressions());
  }

  @Override
  public void visit(ReturnStatementExpression returnStatementExpression) {
    add(returnStatementExpression);
  }

  @Override
  public void visit(SignatureDeclarationExpression signature) {
    add(signature);
  }

  @Override
  public void visit(QuoteExpression quoteExpression) {
   throw new NotImplementedException();
  }

  @Override
  public void visit(AssignmentStatementExpression assignmentStatementExpression) {
    throw new NotImplementedException();
  }

  @Override
  public void visit(NumericExpression expression) {

  }

  @Override
  public void visit(ValDeclarationStatement valDeclarationStatement) {
    add(valDeclarationStatement);
  }

  @Override
  public void visit(WithNsExpression withNsExpression) {

  }

  @Override
  public void visit(MarkupDeclarationExpression markupDeclarationExpression) {
    add(markupDeclarationExpression);
  }

  public <T extends VerbaExpression> void visitAll(Iterable<T> expressions) {
    for (VerbaExpression expression : expressions) {
      expression.accept(this);
    }
  }

}
