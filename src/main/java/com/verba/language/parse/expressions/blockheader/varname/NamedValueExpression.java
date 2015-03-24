package com.verba.language.parse.expressions.blockheader.varname;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.*;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class NamedValueExpression extends VerbaExpression
  implements RValueExpression, TupleItemExpression, MarkupRvalueExpression,
    NamedAndTypedExpression, MathOperandExpression
{
  private final FullyQualifiedNameExpression identifier;
  private TypeConstraintExpression type;

  public NamedValueExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.identifier = FullyQualifiedNameExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
      this.type = TypeConstraintExpression.read(this, lexer);
    }

    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void afterContentsParsed(Build build) {

  }

  @Override
  public void afterSymbolsGenerated(Build build, SymbolTable table) {

  }

  @Override
  public void onResolveSymbols(Build build, SymbolTable table) {

  }

  @Override
  public void onValidate(Build build, SymbolTable table) {

  }

  public static NamedValueExpression read(VerbaExpression parent, Lexer lexer) {
    return new NamedValueExpression(parent, lexer);
  }

  public String representation() {
    if (type != null) return String.format("%s : %s", identifier.representation(), type.representation());
    else return this.identifier.representation();
  }

  @Override
  public String name() {
    return this.identifier.representation();
  }

  @Override
  public boolean hasTypeConstraint() {
    return (this.type != null);
  }

  @Override
  public TypeConstraintExpression typeConstraint() {
    return this.type;
  }

  @Override
  public Symbol resolvedType() { return null; } //return buildProfile.resolvedType(); }
  public QIterable<TupleDeclarationExpression> parameters() { return this.identifier.first().parameterLists(); }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  public boolean hasParameters() {
    return this.identifier.first().hasParameters();
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return null;
  }
}
