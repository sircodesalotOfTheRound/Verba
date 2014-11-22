package com.verba.language.parsing.expressions.statements.flow.branch;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.EnclosureToken;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class IfStatementExpression extends VerbaExpression {
  private RValueExpression testExpression;
  private BlockDeclarationExpression block;

  public IfStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "if");
    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
    this.testExpression = RValueExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static IfStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new IfStatementExpression(parent, lexer);
  }

  @Override
  public QIterable<ExpressionValidator> validators() {
    return null;
  }

  public RValueExpression testExpression() {
    return this.testExpression;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
