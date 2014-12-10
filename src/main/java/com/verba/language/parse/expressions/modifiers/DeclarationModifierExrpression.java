package com.verba.language.parse.expressions.modifiers;

import com.verba.language.graph.symbols.table.tables.Scope;
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

  private LexInfo readModifier(Lexer lexer) {
    if (!KeywordToken.isAccessModifierKeyword(lexer.current().representation())) {
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

  @Override
  public void accept(Scope symbolTable) {
    symbolTable.visit(this);
  }
}
