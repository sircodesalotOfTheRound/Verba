package com.verba;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.categories.TupleItemExpression;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.tools.TestTools;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/3.
 */
public class TestNewExpression {
  @Test
  public void testNewExpression() {
    VerbaMemoizingLexer lexer = TestTools.generateLexerFromString("val item = new Object");
    ValDeclarationStatement statement = VerbaExpression.read(null, lexer).as(ValDeclarationStatement.class);
    RValueExpression rvalue = statement.rvalue();
    NewExpression expression = (NewExpression)rvalue;

    assert (!expression.isAnonymous());
    assert (!expression.hasBlock());
    assert (expression.identifier().representation().equals("Object"));
  }

  @Test
  public void testNewExpressionWithParameters() {
    VerbaMemoizingLexer lexer = TestTools.generateLexerFromString("val item = new Tuple(1, 2)");
    ValDeclarationStatement statement = VerbaExpression.read(null, lexer).as(ValDeclarationStatement.class);
    RValueExpression rvalue = statement.rvalue();
    NewExpression expression = (NewExpression)rvalue;

    QIterable<NumericExpression> parameters = expression.identifier()
      .members()
      .first()
      .parameterLists()
      .first()
      .items()
      .cast(NumericExpression.class);

    assert (!expression.isAnonymous());
    assert (!expression.hasBlock());
    assert (expression.identifier().representation().equals("Tuple"));

    assert (parameters.first().asInt() == 1);
    assert (parameters.second().asInt() == 2);

  }


  @Test
  public void testNewAnonymousExpression() {
    VerbaMemoizingLexer lexer = TestTools.generateLexerFromString("val item = new : Object { val item = 10 }");
    ValDeclarationStatement statement = VerbaExpression.read(null, lexer).as(ValDeclarationStatement.class);
    RValueExpression rvalue = statement.rvalue();
    NewExpression expression = (NewExpression)rvalue;

    assert (expression.isAnonymous());
    assert (expression.hasBlock());

    BlockDeclarationExpression block = expression.block();
    assert (block.single().is(VerbaExpression.class));
  }
}
