package com.verba.language.parsing.expressions.rvalue.simple;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */

public class MathOpExpression extends VerbaExpression {
  LexInfo operationToken;

  public MathOpExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.operationToken = lexer.readCurrentAndAdvance(MathOpToken.class);
    this.closeLexingRegion();
  }

  public LexInfo operator() {
    return this.operationToken;
  }

  public static MathOpExpression read(VerbaExpression parent, Lexer lexer) {
    return new MathOpExpression(parent, lexer);
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
