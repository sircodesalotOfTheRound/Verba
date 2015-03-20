package com.verba.language.parse.expressions.members;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.IdentifierToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class FullyQualifiedNameExpression extends VerbaExpression
  implements TypeConstraintExpression, QIterable<MemberExpression> {

  QList<MemberExpression> fullyQualifiedName = new QList<MemberExpression>();

  private FullyQualifiedNameExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    fullyQualifiedName.add(MemberExpression.read(this, lexer));

    while (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ".")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ".");
      fullyQualifiedName.add(MemberExpression.read(this, lexer));
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

  // TODO: Move this into FullyQualifiedNameBacktrackRule if such a thing exists.
  public static boolean isFullyQualifiedName(Lexer lexer) {
    return lexer.currentIs(IdentifierToken.class);
  }

  public static FullyQualifiedNameExpression read(VerbaExpression parent, Lexer lexer) {
    return new FullyQualifiedNameExpression(parent, lexer);
  }

  public QIterable<MemberExpression> members() {
    return this.fullyQualifiedName;
  }

  public String representation() {
    Iterable<String> fqnItems = this.fullyQualifiedName.map(item -> item.representation());
    return String.join(".", fqnItems);
  }

  @Override
  public Iterator<MemberExpression> iterator() {
    return this.fullyQualifiedName.iterator();
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
