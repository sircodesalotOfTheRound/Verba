package com.verba.language.graph.analysis.expressions.analyzers;

import com.verba.language.graph.analysis.expressions.tools.BuildProfile;
import com.verba.language.parsing.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class WithNsExpressionAnalyzer extends BuildProfile<WithNsExpression> {
  public WithNsExpressionAnalyzer(WithNsExpression expression) {
    super(expression);
  }
}
