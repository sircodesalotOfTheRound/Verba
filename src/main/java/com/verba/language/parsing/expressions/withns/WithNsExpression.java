package com.verba.language.parsing.expressions.withns;

import com.verba.language.graph.analysis.expressions.analyzers.WithNsExpressionAnalyzer;
import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysisBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.lexing.Lexer;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class WithNsExpression extends VerbaExpression {
  private final WithNsExpressionAnalyzer analysis = new WithNsExpressionAnalyzer(this);
  private final FullyQualifiedNameExpression namespace;

  public WithNsExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.namespace = FullyQualifiedNameExpression.read(this, lexer);
  }

  public FullyQualifiedNameExpression namespace() { return this.namespace; }

  @Override
  public ExpressionAnalysisBase expressionAnalysis() {
    return analysis;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  public static WithNsExpression read(VerbaExpression parent, Lexer lexer) {
    return new WithNsExpression(parent, lexer);
  }
}
