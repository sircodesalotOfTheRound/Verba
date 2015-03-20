package com.verba.language.parse.expressions.dependencies;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.literals.QuoteToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-5-20.
 */
@Deprecated
public class GrabExpression extends VerbaExpression {
  private final VerbaExpression resourceName;

  public GrabExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    throw new NotImplementedException();
    //lexer.readCurrentAndAdvance(KeywordToken.class, "grab");
    //this.resourceName = this.readResourceName(lexer);
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

  private VerbaExpression readResourceName(Lexer lexer) {
    if (lexer.currentIs(QuoteToken.class)) {
      return QuoteExpression.read(this, lexer);
    }

    return FullyQualifiedNameExpression.read(this, lexer);
  }

  public String resourceNameAsString() {
    if (resourceName instanceof FullyQualifiedNameExpression)
      return ((FullyQualifiedNameExpression) this.resourceName).representation();

    else return ((QuoteExpression) this.resourceName).representation();
  }


  public static GrabExpression read(VerbaExpression parent, Lexer lexer) {
    return new GrabExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
