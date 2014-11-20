package com.verba.language.expressions.statements.meta;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.operators.OperatorToken;


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

  public VerbaExpression statement() {
    return this.statement;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
