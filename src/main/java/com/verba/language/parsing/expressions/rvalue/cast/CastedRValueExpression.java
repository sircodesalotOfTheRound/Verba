package com.verba.language.parsing.expressions.rvalue.cast;

import com.verba.language.graph.analysis.expressions.tools.BuildProfileBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class CastedRValueExpression extends VerbaExpression implements RValueExpression {
  private TypeDeclarationExpression toType;
  private RValueExpression rvalue;

  private CastedRValueExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
    this.toType = TypeDeclarationExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

    this.rvalue = RValueExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static CastedRValueExpression read(VerbaExpression parent, Lexer lexer) {
    return new CastedRValueExpression(parent, lexer);
  }

  @Override
  public BuildProfileBase buildProfile() {
    return null;
  }

  public TypeDeclarationExpression toType() {
    return this.toType;
  }

  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
