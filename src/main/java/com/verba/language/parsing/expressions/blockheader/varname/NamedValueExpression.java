package com.verba.language.parsing.expressions.blockheader.varname;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysisBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.*;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;

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

  @Override
  public ExpressionAnalysisBase expressionAnalysis() {
    return null;
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
