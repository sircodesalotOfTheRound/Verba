package com.verba.language.parse.expressions.statements.declaration;

import com.verba.language.graph.events.interfaces.VerbaExpressionBuildEvent;
import com.verba.language.graph.events.interfaces.VerbaExpressionBuildEventSubscriptionBase;
import com.verba.language.graph.events.ValDeclarationEventSubscriptionVerbaExpression;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.*;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class ValDeclarationStatement extends VerbaExpression
  implements NamedAndTypedExpression, AssignmentExpression, SymbolTableExpression,
  VerbaExpressionBuildEvent.ContainsEventSubscriptionObject
{

  private ValDeclarationEventSubscriptionVerbaExpression buildProfile = new ValDeclarationEventSubscriptionVerbaExpression(this);
  private NamedValueExpression identifier;
  private RValueExpression rvalue;
  private boolean isMutable;

  private ValDeclarationStatement(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.readExpression(lexer);
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  private void readExpression(Lexer lexer) {
    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.VAL);

    this.isMutable = determineMutability(lexer);
    this.identifier = NamedValueExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, "=")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, "=");
      this.rvalue = RValueExpression.read(this, lexer);
    }

    this.closeLexingRegion();
  }

  private boolean determineMutability(Lexer lexer) {
    if (lexer.currentIs(KeywordToken.class, KeywordToken.MUT)) {
      lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.MUT);
      return true;
    } else {
      return false;
    }
  }

  public static ValDeclarationStatement read(VerbaExpression parent, Lexer lexer) {
    return new ValDeclarationStatement(parent, lexer);
  }

  @Override
  public String name() {
    return this.identifier.name();
  }

  public VerbaExpression nameAsExpression() { return this.identifier.identifier().single(); }

  public boolean isMutable() { return this.isMutable; }

  @Override
  public boolean hasTypeConstraint() {
    return this.identifier.hasTypeConstraint();
  }

  @Override
  public TypeConstraintExpression typeConstraint() {
    return this.identifier.typeConstraint();
  }

  @Override
  public Symbol resolvedType() {
    return this.buildProfile.resolvedType();
  }


  public NamedValueExpression identifier() {
    return this.identifier;
  }

  @Override
  public boolean hasRValue() {
    return this.rvalue != null;
  }

  @Override
  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(Scope symbolTable) {
    symbolTable.visit(this);
  }

  @Override
  public VerbaExpressionBuildEventSubscriptionBase buildEventObject() { return this.buildProfile; }
}
