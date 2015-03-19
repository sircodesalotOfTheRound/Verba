package com.verba.language.parse.expressions.codepage;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parse.lexing.Lexer;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-2-18.
 */
public class CodePageExpressionList extends VerbaExpression
  implements QIterable<VerbaExpression> {

  QList<VerbaExpression> expressions = new QList<>();

  public CodePageExpressionList(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.processPage(lexer);
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

  public static CodePageExpressionList read(VerbaExpression parent, Lexer lexer) {
    return new CodePageExpressionList(parent, lexer);
  }

  public void processPage(Lexer lexer) {
    while (lexer.notEOF()) expressions.add((VerbaExpression) NamedBlockExpression.read(this, lexer));
  }

  public QList<VerbaExpression> expressions() {
    return this.expressions();
  }

  @Override
  public Iterator<VerbaExpression> iterator() {
    return this.expressions.iterator();
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
