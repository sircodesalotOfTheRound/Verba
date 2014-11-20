package com.verba.language.expressions.blockheader.varname;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.*;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class NamedValueExpression extends VerbaExpression
  implements RValueExpression, TupleItemExpression, MarkupRvalueExpression,
    NamedAndTypedExpression, MathOperandExpression
{
  private final FullyQualifiedNameExpression identifier;
  private TypeDeclarationExpression type;

  public NamedValueExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.identifier = FullyQualifiedNameExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
      this.type = TypeDeclarationExpression.read(this, lexer);
    }

    this.closeLexingRegion();
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
  public TypeDeclarationExpression typeDeclaration() {
    return this.type;
  }

  public QIterable<TupleDeclarationExpression> parameters() { return this.identifier.first().parameterLists(); }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  public boolean hasParameters() {
    return this.identifier.first().hasParameters();
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

}
