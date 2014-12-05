package com.verba.language.parse.expressions.blockheader.classes;

import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parse.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class ExtendDeclarationExpression extends VerbaExpression implements NamedBlockExpression {
  private final FullyQualifiedNameExpression identifier;
  private final BlockDeclarationExpression block;
  private final GenericTypeListExpression genericTypeList;

  public ExtendDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "extend");
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.genericTypeList = GenericTypeListExpression.read(this, lexer);

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static ExtendDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new ExtendDeclarationExpression(parent, lexer);
  }

  private String getTypeListString() {
    if (this.genericTypeList == null) return "";
    else return this.genericTypeList.toString();
  }

  public FullyQualifiedNameExpression identifer() {
    return this.identifier;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  public GenericTypeListExpression genericParameters() {
    return this.genericTypeList;
  }


  @Override
  public String name() {
    return this.identifier.members().first().memberName();
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }

  @Override
  public void accept(Scope symbolTable) {

  }
}
