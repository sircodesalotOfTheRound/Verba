package com.verba.language.graph.analysis.expressions.analyzers;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.analysis.expressions.tools.BuildProfile;
import com.verba.language.parsing.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class VerbaCodePageAnalyzer extends BuildProfile<VerbaCodePage> {
  private QList<String> namespaces = new QList<>();

  public VerbaCodePageAnalyzer(VerbaCodePage expression) {
    super(expression);
  }

  @Override
  public void afterParse(BuildAnalysis buildAnalysis) {
    QIterable<String> namespaceRepresentations = expression
      .expressionsByType(WithNsExpression.class)
      .map(ns -> ns.namespace().representation());

    this.namespaces.add(namespaceRepresentations);

    System.out.println("The namespaces is: ");

    for (String namespace : namespaces) {
      System.out.println(namespace);
    }
  }

  public QIterable<String> namespaces() { return namespaces; }
}
