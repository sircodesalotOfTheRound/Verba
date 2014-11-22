package com.verba.language.build.codepage;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parsing.Lexer;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-2-18.
 */
public class CodePageExpressionList extends VerbaExpression
  implements QIterable<VerbaExpression> {

  QList<VerbaExpression> expressions = new QList<>();

  public CodePageExpressionList(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.processPage(lexer);
  }

  public static CodePageExpressionList read(VerbaExpression parent, Lexer lexer) {
    return new CodePageExpressionList(parent, lexer);
  }

  @Override
  public QIterable<ExpressionValidator> validators() {
    return null;
  }

  public void processPage(Lexer lexer) {
    while (lexer.notEOF()) expressions.add((VerbaExpression) NamedBlockExpression.read(this, lexer));
  }

  public QList<VerbaExpression> expressions() {
    return this.expressions();
  }

  @Override
  public Iterator<VerbaExpression> iterator() {
    return this.expressions.iterator();
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
