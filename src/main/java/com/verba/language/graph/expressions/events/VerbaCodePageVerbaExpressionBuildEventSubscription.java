package com.verba.language.graph.expressions.events;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEvent;
import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEventSubscription;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class VerbaCodePageVerbaExpressionBuildEventSubscription extends VerbaExpressionBuildEventSubscription<VerbaCodePage>
  implements VerbaExpressionBuildEvent.NotifyParsingVerbaExpressionBuildEvent
{
  private QList<String> namespaces = new QList<>();

  public VerbaCodePageVerbaExpressionBuildEventSubscription(VerbaCodePage expression) {
    super(expression);
  }

  @Override
  public void afterParse(Build analysis, LitFileRootExpression buildAnalysis) {
    QIterable<String> namespaceRepresentations = this.expression()
      .expressionsByType(WithNsExpression.class)
      .map(ns -> ns.namespace().representation());

    this.namespaces.add(namespaceRepresentations);
  }

  public QIterable<String> namespaces() { return namespaces; }

}
