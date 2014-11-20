package com.verba.language.expressions.statements.declaration;

import com.verba.language.graph.statictyping.SymbolTypeResolver;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.expressions.categories.*;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.OperatorToken;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class ValDeclarationStatement extends VerbaExpression
  implements NamedAndTypedExpression, AssignmentExpression, SymbolTableExpression, ResolvableTypeExpression {

  private NamedValueExpression identifier;
  private RValueExpression rvalue;
  private boolean isMutable;

  private ValDeclarationStatement(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.readExpression(lexer);
  }

  private void readExpression(Lexer lexer) {
    lexer.readCurrentAndAdvance(KeywordToken.class, "val");

    if (lexer.currentIs(KeywordToken.class, "mut")) {
      lexer.readCurrentAndAdvance(KeywordToken.class, "mut");
      this.isMutable = true;
    } else {
      this.isMutable = false;
    }

    this.identifier = NamedValueExpression.read(this, lexer);

    lexer.readCurrentAndAdvance(OperatorToken.class, "=");

    this.rvalue = RValueExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static ValDeclarationStatement read(VerbaExpression parent, Lexer lexer) {
    return new ValDeclarationStatement(parent, lexer);
  }

  @Override
  public String name() {
    return this.identifier.name();
  }

  public VerbaExpression nameAsExpression() { return this.identifier.identifier().single(); }

  public boolean isMutable() { return this.isMutable; }

  @Override
  public boolean hasTypeConstraint() {
    return this.identifier.hasTypeConstraint();
  }

  @Override
  public TypeDeclarationExpression typeDeclaration() {
    return this.identifier.typeDeclaration();
  }


  public NamedValueExpression identifier() {
    return this.identifier;
  }

  @Override
  public boolean hasRValue() {
    return this.rvalue != null;
  }

  @Override
  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {
    symbolTable.visit(this);
  }

  @Override
  public void accept(SymbolTypeResolver resolver) {
    resolver.visit(this);
  }
}
