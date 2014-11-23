package com.verba.language.graph.analysis.expressions.analyzers;

import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysis;
import com.verba.language.parsing.expressions.backtracking.rules.FunctionDeclarationBacktrackRule;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;

import java.beans.Expression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class FunctionExpressionAnalyzer extends ExpressionAnalysis<FunctionDeclarationExpression> {
  public FunctionExpressionAnalyzer(FunctionDeclarationExpression expression) {
    super(expression);
  }

}
