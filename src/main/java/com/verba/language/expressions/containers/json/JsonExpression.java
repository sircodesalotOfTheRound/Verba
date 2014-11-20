package com.verba.language.expressions.containers.json;

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
 * Created by sircodesalot on 14-2-24.
 */
public class JsonExpression extends VerbaExpression implements RValueExpression,
  DataContainerExpression {

  QList<VerbaExpression> jsonPairs = new QList<>();

  private JsonExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.readItems(lexer);
  }

  private void readItems(Lexer lexer) {
    lexer.readCurrentAndAdvance(EnclosureToken.class, "{");

    while (lexer.notEOF() && !lexer.currentIs(EnclosureToken.class, "}")) {
      JsonExpressionPair jsonExpressionPair = JsonExpressionPair.read(this, lexer);
      if (jsonExpressionPair != null) jsonPairs.add(jsonExpressionPair);
      if (lexer.currentIs(OperatorToken.class, ",")) lexer.readCurrentAndAdvance();
    }

    lexer.readCurrentAndAdvance(EnclosureToken.class, "}");
    this.closeLexingRegion();
  }

  @Override
  public QIterable<VerbaExpression> items() {
    return this.jsonPairs;
  }

  public static JsonExpression read(VerbaExpression parent, Lexer lexer) {
    return new JsonExpression(parent, lexer);
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }
}
