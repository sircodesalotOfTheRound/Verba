package com.verba.language.parse.expressions.blockheader.classes;

import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
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
  private FullyQualifiedNameExpression identifier;
  private BlockDeclarationExpression block;
  private GenericTypeListExpression genericTypeList;

  public ExtendDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void onParse(VerbaExpression parent, Lexer lexer) {
    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.EXTEND);
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.genericTypeList = GenericTypeListExpression.read(this, lexer);

    this.block = BlockDeclarationExpression.read(this, lexer);
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
  public void accept(ExpressionTreeVisitor visitor) {

  }

  @Override
  public void accept(Scope symbolTable) {

  }
}
