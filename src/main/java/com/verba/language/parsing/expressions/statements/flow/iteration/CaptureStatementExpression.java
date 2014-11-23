package com.verba.language.parsing.expressions.statements.flow.iteration;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-4-28.
 */
public class CaptureStatementExpression extends VerbaExpression {
  private final QList<FullyQualifiedNameExpression> variables;

  public CaptureStatementExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.variables = this.consumeVariables(lexer);
    this.closeLexingRegion();
  }

  private QList<FullyQualifiedNameExpression> consumeVariables(Lexer lexer) {
    QList<FullyQualifiedNameExpression> variableList = new QList<>();

    lexer.readCurrentAndAdvance(EnclosureToken.class, "[");

    do {
      variableList.add(FullyQualifiedNameExpression.read(this, lexer));
    } while (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ","));

    lexer.readCurrentAndAdvance(EnclosureToken.class, "]");

    return variableList;
  }

  public static CaptureStatementExpression read(VerbaExpression parent, Lexer lexer) {
    return new CaptureStatementExpression(parent, lexer);
  }

  @Override
  public QIterable<ExpressionValidator> validators() {
    return null;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
