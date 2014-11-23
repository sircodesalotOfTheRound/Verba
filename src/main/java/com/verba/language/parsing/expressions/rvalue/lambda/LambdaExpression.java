package com.verba.language.parsing.expressions.rvalue.lambda;

import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysisBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.lambda.LambdaToken;

/**
 * Created by sircodesalot on 14-2-28.
 */
public class LambdaExpression extends VerbaExpression implements RValueExpression {
  private TypeDeclarationExpression lvalue;
  private RValueExpression rvalue;

  public LambdaExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.lvalue = TypeDeclarationExpression.read(this, lexer);

    lexer.readCurrentAndAdvance(LambdaToken.class);

    // Attempt to read RValueExpression
    this.rvalue = RValueExpression.read(this, lexer);
    if (this.rvalue == null) {
      FullyQualifiedNameExpression.read(this, lexer);
    }

    this.closeLexingRegion();
  }

  public static LambdaExpression read(VerbaExpression parent, Lexer lexer) {
    return new LambdaExpression(parent, lexer);
  }

  @Override
  public ExpressionAnalysisBase expressionAnalysis() {
    return null;
  }

  public TypeDeclarationExpression lvalue() {
    return this.lvalue;
  }

  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
