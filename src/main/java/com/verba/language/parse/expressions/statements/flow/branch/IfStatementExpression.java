package com.verba.language.parse.expressions.statements.flow.branch;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.categories.BranchExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class IfStatementExpression extends VerbaExpression
  implements BranchExpression
{
  private RValueExpression testExpression;
  private BlockDeclarationExpression block;

  public IfStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.IF);
    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
    this.testExpression = RValueExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void parse(VerbaExpression parent, Lexer lexer) {

  }

  public static IfStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new IfStatementExpression(parent, lexer);
  }

  public RValueExpression testExpression() {
    return this.testExpression;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
