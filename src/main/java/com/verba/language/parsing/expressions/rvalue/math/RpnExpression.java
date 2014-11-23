package com.verba.language.parsing.expressions.rvalue.math;

import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysisBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class RpnExpression extends VerbaExpression implements RValueExpression {
  // Should probabaly be a tree rather than a list.
  private final RpnMap expressions;

  private RpnExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.expressions = new RpnMap(parent, lexer);
    this.closeLexingRegion();
  }

  private boolean isNextMathToken(Lexer lexer) {
    boolean isIt = false;

    lexer.setUndoPoint();
    lexer.advance();
    if (lexer.notEOF() && lexer.currentIs(MathOpToken.class)) {
      isIt = true;
    }
    lexer.rollbackToUndoPoint();

    return isIt;
  }

  public static RpnExpression read(VerbaExpression parent, Lexer lexer) {
    return new RpnExpression(parent, lexer);
  }

  @Override
  public ExpressionAnalysisBase expressionAnalysis() {
    return null;
  }

  public RpnMap expressions() {
    return this.expressions;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
