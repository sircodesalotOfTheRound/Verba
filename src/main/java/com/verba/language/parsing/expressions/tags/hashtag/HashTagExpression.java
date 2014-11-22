package com.verba.language.parsing.expressions.tags.hashtag;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.MetaTagExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.EnclosureToken;
import com.verba.language.parsing.tokens.operators.tags.HashTagToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class HashTagExpression extends VerbaExpression implements MetaTagExpression {
  public FullyQualifiedNameExpression identifier;

  private HashTagExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(HashTagToken.class);
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, "]");
    this.closeLexingRegion();
  }

  public static HashTagExpression read(VerbaExpression parent, Lexer lexer) {
    return new HashTagExpression(parent, lexer);
  }

  @Override
  public QIterable<ExpressionValidator> validators() {
    return null;
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
