package com.verba.language.parse.expressions.block;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-2-18.
 */
public class BlockDeclarationExpression extends VerbaExpression
  implements QIterable<VerbaExpression>, SymbolTableExpression {

  private final QList<VerbaExpression> expressions = new QList<>();

  private BlockDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    // If the first token isn't an open brace, then just return empty
    if (!lexer.currentIs(EnclosureToken.class, EnclosureToken.OPEN_BRACE)) {
      return;
    }

    lexer.readCurrentAndAdvance(EnclosureToken.class, EnclosureToken.OPEN_BRACE);

    while (lexer.notEOF() && !lexer.currentIs(EnclosureToken.class, "}")) {
      VerbaExpression result = VerbaExpression.read(this, lexer);

      if (result != null) this.expressions.add(result);
      else throw new NotImplementedException();
    }

    lexer.readCurrentAndAdvance(EnclosureToken.class, EnclosureToken.CLOSE_BRACE);

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

  public boolean hasItems() {
    return this.expressions.any();
  }

  public QIterable<VerbaExpression> expressions() {
    return this.expressions;
  }

  public static BlockDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new BlockDeclarationExpression(parent, lexer);
  }

  @Override
  public Iterator<VerbaExpression> iterator() {
    return this.expressions.iterator();
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(Scope symbolTable) {
    symbolTable.visit(this);
  }
}
