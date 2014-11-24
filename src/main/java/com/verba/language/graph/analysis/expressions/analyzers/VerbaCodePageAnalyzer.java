package com.verba.language.graph.analysis.expressions.analyzers;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysis;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class VerbaCodePageAnalyzer extends ExpressionAnalysis<VerbaCodePage> {
  private QList<String> namespaces = new QList<>();

  public VerbaCodePageAnalyzer(VerbaCodePage expression) {
    super(expression);
  }

  @Override
  public void afterParse(BuildAnalysis buildAnalysis) {
    super.afterParse(buildAnalysis);
  }

  public QIterable<String> namespaces() { return namespaces; }
}
