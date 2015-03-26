package com.verba.expressions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.TupleItemExpression;
import com.verba.language.parse.expressions.immediate.ImmediateFunctionExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.UtfExpression;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.testtools.TestTools;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/26.
 */
public class TestImmediateFunction {
  @Test
  public void testImmediateFunction() {
    VerbaMemoizingLexer lexer = TestTools
      .generateLexerFromString("(fn immediate_function(first, second third) { })(1, \"two\", three)");

    ImmediateFunctionExpression expression = VerbaExpression.read(null, lexer).as(ImmediateFunctionExpression.class);

    assert(expression.function().name().equals("immediate_function"));

    // Test the arguments (1, "two", three)
    QIterable<TupleItemExpression> arguments = expression.arguments().items();
    assert (arguments.first() instanceof NumericExpression);
    assert (arguments.ofType(UtfExpression.class).any());
    assert (arguments.last() instanceof NamedValueExpression);
  }

  @Test
  public void asyncImmediateFunction() {
    VerbaMemoizingLexer lexer = TestTools.generateLexerFromString("(async fn { return 10 })");
    VerbaExpression expression = VerbaExpression.read(null, lexer);

    assert(expression.is(ImmediateFunctionExpression.class));

    ImmediateFunctionExpression immediateFunction = expression.as(ImmediateFunctionExpression.class);
    assert (immediateFunction.isModified());
    assert (immediateFunction.isAsynchronous());
    assert (immediateFunction.function().isAnonymous());
  }
}
