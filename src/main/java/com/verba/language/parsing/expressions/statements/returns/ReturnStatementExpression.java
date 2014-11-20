package com.verba.language.parsing.expressions.statements.returns;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-22.
 */

public class ReturnStatementExpression extends VerbaExpression {
  private RValueExpression value;

  public ReturnStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    int currentLine = lexer.current().line();
    lexer.readCurrentAndAdvance(KeywordToken.class, "return");

    if (lexer.current().line() == currentLine) {
      this.value = RValueExpression.read(this, lexer);
    }

    this.closeLexingRegion();
  }

  public static ReturnStatementExpression read(VerbaExpression expression, Lexer lexer) {
    return new ReturnStatementExpression(expression, lexer);
  }

  public RValueExpression value() {
    return this.value;
  }

  public boolean hasValue() {
    return this.value != null;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

}
