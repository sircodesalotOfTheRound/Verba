package com.verba.language.expressions.containers.set;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.DataContainerExpression;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.EnclosureToken;
import com.verba.language.parsing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class SetDeclarationExpression extends VerbaExpression
  implements DataContainerExpression, RValueExpression {
  private final QList<VerbaExpression> items = new QList<>();

  private SetDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(EnclosureToken.class, "{");

    while (lexer.notEOF()
      && !lexer.currentIs(EnclosureToken.class, "}")
      && !lexer.currentIs(OperatorToken.class, ",")) {

      RValueExpression item = RValueExpression.read(this, lexer);
      this.items.add((VerbaExpression) item);

      if (lexer.currentIs(OperatorToken.class, ",")) lexer.readCurrentAndAdvance(OperatorToken.class, ",");
      else break; // Shennanigans
    }

    lexer.readCurrentAndAdvance(EnclosureToken.class, "}");
    this.closeLexingRegion();
  }

  public QIterable<VerbaExpression> items() {
    return this.items;
  }

  public static SetDeclarationExpression read(VerbaExpression expression, Lexer lexer) {
    return new SetDeclarationExpression(expression, lexer);
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
