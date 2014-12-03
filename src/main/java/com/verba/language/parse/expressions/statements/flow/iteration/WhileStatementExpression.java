package com.verba.language.parse.expressions.statements.flow.iteration;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class WhileStatementExpression extends VerbaExpression {
  private RValueExpression testCondition;
  private BlockDeclarationExpression block;

  public WhileStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "while");
    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
    this.testCondition = RValueExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static WhileStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new WhileStatementExpression(parent, lexer);
  }

  public RValueExpression testCondition() {
    return this.testCondition;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
