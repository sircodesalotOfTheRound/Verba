package com.verba.language.graph.analysis.expressions.analyzers;

import com.verba.language.graph.analysis.expressions.tools.BuildProfile;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class FunctionExpressionAnalyzer extends BuildProfile<FunctionDeclarationExpression> {
  public FunctionExpressionAnalyzer(FunctionDeclarationExpression expression) {
    super(expression);
  }

}
