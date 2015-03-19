package com.verba.language.graph.expressions.events;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.parse.expressions.LitFileRootExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class VerbaCodePageVerbaExpressionBuildEventSubscription
{
  private QList<String> namespaces = new QList<>();

  public void afterParse(Build analysis, LitFileRootExpression buildAnalysis) {
    /*QIterable<String> namespaceRepresentations = this.expression()
      .expressionsByType(WithNsExpression.class)
      .map(ns -> ns.namespace().representation());

    this.namespaces.add(namespaceRepresentations);*/
  }

  public QIterable<String> namespaces() { return namespaces; }

}
