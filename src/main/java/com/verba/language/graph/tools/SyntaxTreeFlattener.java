package com.verba.language.graph.tools;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.TaskDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parsing.expressions.containers.json.JsonExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parsing.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parsing.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parsing.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parsing.expressions.withns.WithNsExpression;
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

  public void visit(TraitDeclarationExpression node) {
    add(node);
    this.visitAll(node.block());
  }

  public void visit(StaticSpaceExpression node) {
    add(node);
    this.visitAll(node.pages());
  }

  public void visit(NamedValueExpression node) {
    add(node);

    if (node.hasTypeConstraint()) {
      VerbaExpression expression = (VerbaExpression)node.typeDeclaration();
      expression.accept(this);
    }
  }

  // Todo: Should this also read statements as well? Probabaly?

  public void visit(ClassDeclarationExpression classDeclaration) {
    add(classDeclaration);

    this.visitAll(classDeclaration.block());
  }

  public void visit(FunctionDeclarationExpression function) {
    add(function);

    this.visitAll(function.block());
  }

  public void visit(TaskDeclarationExpression task) {
    add(task);

    this.visitAll(task.block());
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
    this.visitAll(page.expressions());
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

  }

  @Override
  public void visit(WithNsExpression withNsExpression) {

  }

  public <T extends VerbaExpression> void visitAll(Iterable<T> expressions) {
    for (VerbaExpression expression : expressions) {
      expression.accept(this);
    }
  }

}
