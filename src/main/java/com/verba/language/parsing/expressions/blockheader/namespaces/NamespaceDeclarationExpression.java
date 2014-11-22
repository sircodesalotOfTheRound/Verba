package com.verba.language.parsing.expressions.blockheader.namespaces;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class NamespaceDeclarationExpression extends VerbaExpression implements NamedBlockExpression {
  private final FullyQualifiedNameExpression identifier;
  private final BlockDeclarationExpression block;

  public NamespaceDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "namespace");

    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static NamespaceDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new NamespaceDeclarationExpression(parent, lexer);
  }

  @Override
  public QIterable<ExpressionValidator> validators() {
    return null;
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  @Override
  public String name() {
    return this.identifier.members().first().memberName();
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {
    symbolTable.visit(this);
  }
}
