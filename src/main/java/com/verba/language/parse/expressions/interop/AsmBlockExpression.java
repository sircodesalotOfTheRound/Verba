package com.verba.language.parse.expressions.interop;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 15/3/10.
 */
public class AsmBlockExpression extends VerbaExpression {
  private final BlockDeclarationExpression block;
  private final FullyQualifiedNameExpression architecture;

  public AsmBlockExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.architecture = readAssemblerType(lexer);
    this.block = readBlock(lexer);
  }

  public FullyQualifiedNameExpression readAssemblerType(Lexer lexer) {
    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.ASM);
    lexer.readCurrentAndAdvance(OperatorToken.class, ":");

    return FullyQualifiedNameExpression.read(this, lexer);
  }

  public BlockDeclarationExpression readBlock(Lexer lexer) {
    return BlockDeclarationExpression.read(this, lexer);
  }

  public FullyQualifiedNameExpression architecture() { return this.architecture; }
  public BlockDeclarationExpression block() { return this.block; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  public static AsmBlockExpression read(VerbaExpression parent, Lexer lexer) {
    return new AsmBlockExpression(parent, lexer);
  }
}
