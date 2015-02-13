package com.verba.language.parse.expressions.statements.returns;

import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSubscriptionBase;
import com.verba.language.build.event.subscriptions.ReturnStatementEventSubscription;
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
  implements BuildEvent.ContainsEventSubscriptionObject
{
  private RValueExpression value;

  public ReturnStatementEventSubscription eventSubscription = new ReturnStatementEventSubscription(this);

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

  @Override
  public void parse(VerbaExpression parent, Lexer lexer) {

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
  public BuildEventSubscriptionBase buildEventObject() { return this.eventSubscription; }
}
