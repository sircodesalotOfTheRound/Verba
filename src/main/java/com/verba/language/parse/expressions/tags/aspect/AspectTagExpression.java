package com.verba.language.parse.expressions.tags.aspect;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.classes.InjectedDeclarationExpression;
import com.verba.language.parse.expressions.categories.MetaTagExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;
import com.verba.language.parse.tokens.operators.tags.AspectTagToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class AspectTagExpression extends VerbaExpression implements MetaTagExpression {
  public FullyQualifiedNameExpression identifier;
  public VerbaExpression type;

  private AspectTagExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(AspectTagToken.class);

    // Check if this is a named aspect expression.
    // @| name : Aspect
    if (lexer.peekToEndOfLine().contains(OperatorToken.class, ":")) {
      this.identifier = FullyQualifiedNameExpression.read(this, lexer);

      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
    }

    this.type = this.readType(lexer);

    // Read the closing ']'
    lexer.readCurrentAndAdvance(EnclosureToken.class, "]");
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

  private VerbaExpression readType(Lexer lexer) {
    // @[ ... : new AspectFQN ]
    if (lexer.currentIs(KeywordToken.class, KeywordToken.NEW)) {
      return NewExpression.read(this, lexer);
    }

    // @ [ ... : inject AspectFQN ]
    else if (lexer.currentIs(KeywordToken.class, KeywordToken.INJECT)) {
      return InjectedDeclarationExpression.read(this, lexer);
    }

    // @ [ ... : AspectFQN ] (is the same as new aspectFQN)
    else if (FullyQualifiedNameExpression.isFullyQualifiedName(lexer)) {
      return FullyQualifiedNameExpression.read(this, lexer);
    }

    throw new NotImplementedException();
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  public VerbaExpression aspectType() {
    return this.type;
  }

  public boolean isNewedAspect() {
    return this.type != null && this.type.is(NewExpression.class);
  }

  public boolean isInjectedAspect() {
    throw new NotImplementedException();
  }

  public boolean hasIdentifier() {
    return this.identifier != null;
  }

  public static AspectTagExpression read(VerbaExpression parent, Lexer lexer) {
    return new AspectTagExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
