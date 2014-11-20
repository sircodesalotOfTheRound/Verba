package com.verba.language.parsing.expressions.statements.flow.iteration;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.EnclosureToken;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class ForStatementExpression extends VerbaExpression {
  private TypeDeclarationExpression variable;
  private RValueExpression spanExpression;
  private BlockDeclarationExpression block;

  private ForStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.readContent(lexer);
    this.closeLexingRegion();
  }

  private void readContent(Lexer lexer) {
    lexer.readCurrentAndAdvance(KeywordToken.class, "for");
    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");

    this.variable = TypeDeclarationExpression.read(this, lexer);

    lexer.readCurrentAndAdvance(OperatorToken.class, ":");
    this.spanExpression = RValueExpression.read(this, lexer);

    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");
    this.block = BlockDeclarationExpression.read(this, lexer);

  }

  public static ForStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new ForStatementExpression(parent, lexer);
  }

  public TypeDeclarationExpression variable() {
    return this.variable;
  }

  public RValueExpression spanExpression() {
    return this.spanExpression;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
