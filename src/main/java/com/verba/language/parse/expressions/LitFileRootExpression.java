package com.verba.language.parse.expressions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;

/**
 * Created by sircodesalot on 14-5-14.
 */
public class LitFileRootExpression extends VerbaExpression implements SymbolTableExpression {
  private QList<VerbaExpression> allExpressions = new QList<>();
  private Partition<Class, VerbaExpression> expressionsByType;
  private QList<VerbaSourceCodeFile> pages;

  public LitFileRootExpression() {
    super(null, null);
  }

  public LitFileRootExpression(Iterable<VerbaSourceCodeFile> pages) {
    super(null, null);

    this.pages = new QList<>(pages);
    this.allExpressions = extractExpressionsFromPages(this.pages);
    this.expressionsByType = partitionExpressions(allExpressions);
  }

  public LitFileRootExpression(VerbaSourceCodeFile... pages) {
    super(null, null);

    this.pages = new QList<>(pages);
    this.allExpressions = extractExpressionsFromPages(this.pages);
    this.expressionsByType = partitionExpressions(allExpressions);
  }

  private Partition<Class, VerbaExpression> partitionExpressions(QIterable<VerbaExpression> expressions) {
    return this.allExpressions.parition(Object::getClass);
  }

  private QList<VerbaExpression> extractExpressionsFromPages(QIterable<VerbaSourceCodeFile> pages) {
    QList<VerbaExpression> allExpressionsFromPage = new QList<>(pages.cast(VerbaExpression.class));
    allExpressionsFromPage.add(pages.flatten(VerbaSourceCodeFile::allExpressions));

    return allExpressionsFromPage;
  }

  public boolean containsExpressionsOfType(Class type) {
    return this.expressionsByType.containsKey(type);
  }

  public QIterable<VerbaSourceCodeFile> pages() { return this.pages; }

  public QIterable<VerbaExpression> allExpressions() { return this.allExpressions; }

  public Partition<Class, VerbaExpression> expressionsByType() { return this.expressionsByType; }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(Scope symbolTable) { symbolTable.visit(this); }

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

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return null;
  }
}
