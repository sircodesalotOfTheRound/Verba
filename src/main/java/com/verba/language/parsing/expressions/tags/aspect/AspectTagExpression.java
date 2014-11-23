package com.verba.language.parsing.expressions.tags.aspect;

import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysisBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.MetaTagExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;
import com.verba.language.parsing.tokens.operators.tags.AspectTagToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class AspectTagExpression extends VerbaExpression implements MetaTagExpression {
  public FullyQualifiedNameExpression identifier;
  public VerbaExpression type;

  private AspectTagExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(AspectTagToken.class);

    // Check if this is a named aspect expression.
    // @| name : Aspect
    if (lexer.peekToEndOfLine().contains(OperatorToken.class, ":")) {
      this.identifier = FullyQualifiedNameExpression.read(this, lexer);

      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
    }

    this.type = this.readType(lexer);

    // Read the closing ']'
    lexer.readCurrentAndAdvance(EnclosureToken.class, "]");
    this.closeLexingRegion();
  }

  private VerbaExpression readType(Lexer lexer) {
    // @[ ... : new AspectFQN ]
    if (lexer.currentIs(KeywordToken.class, "new")) {
      return NewExpression.read(this, lexer);
    }

    // @ [ ... : inject AspectFQN ]
    else if (lexer.currentIs(KeywordToken.class, "inject")) {
      throw new NotImplementedException();
    }

    // @ [ ... : AspectFQN ] (is the same as new aspectFQN)
    else if (FullyQualifiedNameExpression.IsFullyQualifiedName(lexer)) {
      throw new NotImplementedException();
    }

    throw new NotImplementedException();
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  public VerbaExpression aspectType() {
    return this.type;
  }

  public boolean isNewedAspect() {
    return this.type != null && this.type.is(NewExpression.class);
  }

  public boolean isInjectedAspect() {
    throw new NotImplementedException();
  }

  public boolean hasIdentifier() {
    return this.identifier != null;
  }

  public static AspectTagExpression read(VerbaExpression parent, Lexer lexer) {
    return new AspectTagExpression(parent, lexer);
  }

  @Override
  public ExpressionAnalysisBase expressionAnalysis() {
    return null;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
