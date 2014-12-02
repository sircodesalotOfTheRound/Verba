package com.verba.language.parsing.expressions.statements.declaration;

import com.verba.language.graph.analysis.expressions.tools.BuildProfileBase;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.categories.*;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class ValDeclarationStatement extends VerbaExpression
  implements NamedAndTypedExpression, AssignmentExpression, SymbolTableExpression {

  private NamedValueExpression identifier;
  private RValueExpression rvalue;
  private boolean isMutable;

  private ValDeclarationStatement(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.readExpression(lexer);
  }

  private void readExpression(Lexer lexer) {
    lexer.readCurrentAndAdvance(KeywordToken.class, "val");

    this.isMutable = determineMutability(lexer);
    this.identifier = NamedValueExpression.read(this, lexer);

    lexer.readCurrentAndAdvance(OperatorToken.class, "=");

    this.rvalue = RValueExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  private boolean determineMutability(Lexer lexer) {
    if (lexer.currentIs(KeywordToken.class, "mut")) {
      lexer.readCurrentAndAdvance(KeywordToken.class, "mut");
      return true;
    } else {
      return false;
    }
  }

  public static ValDeclarationStatement read(VerbaExpression parent, Lexer lexer) {
    return new ValDeclarationStatement(parent, lexer);
  }

  @Override
  public BuildProfileBase buildProfile() {
    return null;
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

}
