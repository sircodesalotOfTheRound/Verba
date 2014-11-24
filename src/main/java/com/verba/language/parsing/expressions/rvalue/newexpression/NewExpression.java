package com.verba.language.parsing.expressions.rvalue.newexpression;

import com.verba.language.graph.analysis.expressions.tools.BuildProfileBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.MathOperandExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.identifiers.IdentifierToken;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.enclosure.EnclosureToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class NewExpression extends VerbaExpression implements RValueExpression, MathOperandExpression {
  private TypeDeclarationExpression expression;

  public NewExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "new");
    this.expression = this.parseExpression(lexer);

    if (lexer.currentIs(EnclosureToken.class, "(")) {
      lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
      lexer.readCurrentAndAdvance(EnclosureToken.class, ")");
    }

    this.closeLexingRegion();
  }

  private TypeDeclarationExpression parseExpression(Lexer lexer) {
    if (lexer.currentIs(IdentifierToken.class)) return TypeDeclarationExpression.read(this, lexer);
    else throw new NotImplementedException();
  }

  public static NewExpression read(VerbaExpression parent, Lexer lexer) {
    return new NewExpression(parent, lexer);
  }

  @Override
  public BuildProfileBase buildProfile() {
    return null;
  }

  public TypeDeclarationExpression expression() {
    return this.expression;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
