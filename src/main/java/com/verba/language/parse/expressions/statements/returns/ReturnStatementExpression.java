package com.verba.language.parse.expressions.statements.returns;

import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEvent;
import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEventSubscriptionBase;
import com.verba.language.graph.expressions.events.ReturnStatementEventSubscriptionVerbaExpression;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-22.
 */

public class ReturnStatementExpression extends VerbaExpression
  implements VerbaExpressionBuildEvent.ContainsEventSubscriptionObject
{
  private RValueExpression value;

  public ReturnStatementEventSubscriptionVerbaExpression eventSubscription = new ReturnStatementEventSubscriptionVerbaExpression(this);

  public ReturnStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    int currentLine = lexer.current().line();
    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.RETURN);

    if (lexer.current().line() == currentLine) {
      this.value = RValueExpression.read(this, lexer);
    }

    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

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
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  public Symbol returnType() {
    return eventSubscription.returnType();
  }
  @Override
  public VerbaExpressionBuildEventSubscriptionBase buildEventObject() { return this.eventSubscription; }
}
