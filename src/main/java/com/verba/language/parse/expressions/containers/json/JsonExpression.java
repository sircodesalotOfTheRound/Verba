package com.verba.language.parse.expressions.containers.json;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.DataContainerExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class JsonExpression extends VerbaExpression implements RValueExpression,
  DataContainerExpression<JsonExpressionPair> {

  QList<JsonExpressionPair> jsonPairs = new QList<>();

  private JsonExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.readItems(lexer);
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

  private void readItems(Lexer lexer) {
    lexer.readCurrentAndAdvance(EnclosureToken.class, "{");

    while (lexer.notEOF() && !lexer.currentIs(EnclosureToken.class, "}")) {
      JsonExpressionPair jsonExpressionPair = JsonExpressionPair.read(this, lexer);
      if (jsonExpressionPair != null) jsonPairs.add(jsonExpressionPair);
      if (lexer.currentIs(OperatorToken.class, ",")) lexer.readCurrentAndAdvance();
    }

    lexer.readCurrentAndAdvance(EnclosureToken.class, "}");
    this.closeLexingRegion();
  }

  @Override
  public QIterable<JsonExpressionPair> items() {
    return this.jsonPairs;
  }

  public static JsonExpression read(VerbaExpression parent, Lexer lexer) {
    return new JsonExpression(parent, lexer);
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
