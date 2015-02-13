package com.verba.language.parse.expressions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSubscriptionBase;
import com.verba.language.build.event.subscriptions.StaticSpaceBuildEventSubscription;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-5-14.
 */
public class LitFileRootExpression extends VerbaExpression implements SymbolTableExpression, BuildEvent.ContainsEventSubscriptionObject {
  private StaticSpaceBuildEventSubscription buildProfile = new StaticSpaceBuildEventSubscription(this);
  private QList<VerbaExpression> allExpressions = new QList<>();
  private Partition<Class, VerbaExpression> expressionsByType;
  private QList<VerbaCodePage> pages;

  public LitFileRootExpression() {
    super(null, null);
  }

  public LitFileRootExpression(Iterable<VerbaCodePage> pages) {
    super(null, null);

    this.pages = new QList<>(pages);
    this.allExpressions = extractExpressionsFromPages(this.pages);
    this.expressionsByType = partitionExpressions(allExpressions);
  }

  public LitFileRootExpression(VerbaCodePage... pages) {
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
    allExpressionsFromPage.add(pages.flatten(VerbaCodePage::allExpressions));

    return allExpressionsFromPage;
  }

  public boolean containsExpressionsOfType(Class type) {
    return this.expressionsByType.containsKey(type);
  }

  public QIterable<VerbaCodePage> pages() { return this.pages; }

  public QIterable<VerbaExpression> allExpressions() { return this.allExpressions; }

  public Partition<Class, VerbaExpression> expressionsByType() { return this.expressionsByType; }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(Scope symbolTable) { symbolTable.visit(this); }

  @Override
  public BuildEventSubscriptionBase buildEventObject() { return buildProfile; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void onParse(VerbaExpression parent, Lexer lexer) {

  }
}
