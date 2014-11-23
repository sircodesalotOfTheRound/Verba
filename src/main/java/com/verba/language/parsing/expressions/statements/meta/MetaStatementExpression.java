package com.verba.language.parsing.expressions.statements.meta;

import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysisBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;


/**
 * Created by sircodesalot on 14-2-28.
 */
public class MetaStatementExpression extends VerbaExpression {
  private final VerbaExpression statement;

  public MetaStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(OperatorToken.class, "@");
    this.statement = VerbaExpression.read(this, lexer);

    this.closeLexingRegion();
  }

  public static MetaStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new MetaStatementExpression(parent, lexer);
  }

  @Override
  public ExpressionAnalysisBase expressionAnalysis() {
    return null;
  }

  public VerbaExpression statement() {
    return this.statement;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
