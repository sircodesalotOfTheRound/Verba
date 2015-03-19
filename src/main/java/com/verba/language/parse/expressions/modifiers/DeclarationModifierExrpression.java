package com.verba.language.parse.expressions.modifiers;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class DeclarationModifierExrpression extends VerbaExpression implements SymbolTableExpression {
  private final LexInfo modifier;
  private final VerbaExpression modifiedExpression;

  public DeclarationModifierExrpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.modifier = readModifier(lexer);
    this.modifiedExpression = VerbaExpression.read(this, lexer);
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

  private LexInfo readModifier(Lexer lexer) {
    String currentToken = lexer.current().representation();
    if (!isModifier(currentToken)) {
      throw new CompilerException("DeclarationModifierExpressions must read modifier");
    }

    return lexer.readCurrentAndAdvance();
  }

  public LexInfo modifier() { return this.modifier; }
  public VerbaExpression modifiedExpression() { return this.modifiedExpression; }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  public static DeclarationModifierExrpression read(VerbaExpression parent, Lexer lexer) {
    return new DeclarationModifierExrpression(parent, lexer);
  }

  public static boolean isModifier(String representation) {
    return KeywordToken.isAccessModifierKeyword(representation) ||
      KeywordToken.isFunctionModifierExpression(representation);
  }

  @Override
  public void accept(Scope symbolTable) {
    symbolTable.visit(this);
  }
}
