package com.verba.language.parsing.expressions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.expressions.tools.BuildProfileBase;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.categories.SymbolTableExpression;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTypeMetadata;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-5-14.
 */
public class StaticSpaceExpression extends VerbaExpression implements SymbolTableExpression {
  private final QList<VerbaExpression> allExpressions;
  private final Partition<Class, VerbaExpression> expressionsByType;
  private final QList<VerbaCodePage> pages;

  public StaticSpaceExpression(Iterable<VerbaCodePage> pages) {
    super(null, null);

    this.pages = new QList<>(pages);
    this.allExpressions = extractExpressionsFromPages(this.pages);
    this.expressionsByType = partitionExpressions(allExpressions);
  }

  public StaticSpaceExpression(VerbaCodePage ... pages) {
    super(null, null);

    this.pages = new QList<>(pages);
    this.allExpressions = extractExpressionsFromPages(this.pages);
    this.expressionsByType = partitionExpressions(allExpressions);
  }

  private Partition<Class, VerbaExpression> partitionExpressions(QIterable<VerbaExpression> expressions) {
    return this.allExpressions.parition(Object::getClass);
  }

  private QList<VerbaExpression> extractExpressionsFromPages(QIterable<VerbaCodePage> pages) {
    QList<VerbaExpression> allExpressionsFromPage = new QList<>(pages.cast(VerbaExpression.class));
    allExpressionsFromPage.add(pages.flatten(VerbaCodePage::expressions));

    return allExpressionsFromPage;
  }

  public QIterable<VerbaCodePage> pages() { return this.pages; }

  public QIterable<VerbaExpression> allExpressions() { return this.allExpressions; }

  public Partition<Class, VerbaExpression> expressionsByType() { return this.expressionsByType; }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) { symbolTable.visit(this); }

  @Override
  public BuildProfileBase buildProfile() {
    return null;
  }
}
