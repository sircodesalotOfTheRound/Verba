package com.verba;

import com.verba.language.parse.codestream.StringBasedCodeStream;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.MathExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.language.parse.tokens.operators.mathop.AddOpToken;
import com.verba.language.parse.tokens.operators.mathop.SubtractOpToken;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/17.
 */
public class TestMathExpressions {
  @Test
  public void testMathExpressions() {
    //            +
    //         /       \
    //  variable       -
    //              /      \
    //             4  Class.function()

    StringBasedCodeStream codeStream = new StringBasedCodeStream("variable + 4 - Class.function()");
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("testfile.v", codeStream);
    MathExpression expression = (MathExpression) RValueExpression.read(null, lexer);

    assert(expression.lhs().is(NamedValueExpression.class));
    assert(expression.operator().is(AddOpToken.class));
    assert(expression.rhs().is(MathExpression.class));

    MathExpression rhs = (MathExpression) expression.rhs();
    assert(rhs.lhs().is(NumericExpression.class));
    assert(rhs.operator().is(SubtractOpToken.class));
    assert(rhs.rhs().is(NamedValueExpression.class));
  }
}
