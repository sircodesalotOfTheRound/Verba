package com.verba.language.parse.expressions.rvalue.newexpression;

import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.categories.MathOperandExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class NewExpression extends VerbaExpression
  implements RValueExpression, MathOperandExpression
{
  private final BlockDeclarationExpression block;
  private final FullyQualifiedNameExpression identifier;
  private final boolean isAnonymous;

  public NewExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.NEW);
    this.isAnonymous = this.determineIsAnonymous(lexer);
    this.identifier = this.parseIdentifier(lexer);
    this.block = this.readBlock(lexer);

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

  private boolean determineIsAnonymous(Lexer lexer) {
    if (lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance();
      return true;
    } else {
      return false;
    }
  }

  private BlockDeclarationExpression readBlock(Lexer lexer) {
    if (lexer.currentIs(EnclosureToken.class, EnclosureToken.OPEN_BRACE)) {
      return BlockDeclarationExpression.read(this, lexer);
    } else {
      return null;
    }
  }

  private FullyQualifiedNameExpression parseIdentifier(Lexer lexer) {
    // TODO: Remove this section.
    /*if (lexer.currentIs(IdentifierToken.class)) return TypeConstraintExpression.read(this, lexer);
    else throw new NotImplementedException();*/

    return FullyQualifiedNameExpression.read(null, lexer);
  }

  public static NewExpression read(VerbaExpression parent, Lexer lexer) {
    return new NewExpression(parent, lexer);
  }

  public BlockDeclarationExpression block() { return this.block; }
  public FullyQualifiedNameExpression identifier() { return this.identifier; }
  public boolean isAnonymous() { return this.isAnonymous; }
  public boolean hasBlock() { return this.block != null; }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return null;
  }
}
