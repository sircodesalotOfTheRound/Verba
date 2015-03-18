package com.verba.expressions;

import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.AddOpToken;
import com.verba.language.parse.tokens.operators.mathop.DivideOpToken;
import com.verba.language.parse.tokens.operators.mathop.MultiplyOpToken;
import com.verba.language.parse.tokens.operators.mathop.SubtractOpToken;
import com.verba.testtools.TestTools;
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

    Lexer lexer = TestTools.generateLexerFromString("variable + 4 - Class.function()");
    InfixExpression expression = (InfixExpression) RValueExpression.read(null, lexer);

    assert(expression.lhs().is(NamedValueExpression.class));
    assert(expression.operator().is(AddOpToken.class));
    assert(expression.rhs().is(InfixExpression.class));

    InfixExpression rhs = (InfixExpression) expression.rhs();
    assert(rhs.lhs().is(NumericExpression.class));
    assert(rhs.operator().is(SubtractOpToken.class));
    assert(rhs.rhs().is(NamedValueExpression.class));
  }

  @Test
  public void testInfixOrder() {
    //        +
    //    /      \
    //    *       *
    //  /  \    /  \
    // 4   3   2  2

    Lexer lexer = TestTools.generateLexerFromString("1 * 2 + 3 / 4");
    InfixExpression addition = (InfixExpression) RValueExpression.read(null, lexer);

    assert(addition.operator().getToken() instanceof AddOpToken);

    InfixExpression multiply = addition.lhs().as(InfixExpression.class);
    InfixExpression divide = addition.rhs().as(InfixExpression.class);

    assert(multiply.operator().getToken() instanceof MultiplyOpToken);
    assert(divide.operator().getToken() instanceof DivideOpToken);

    assert(multiply.lhs().as(NumericExpression.class).asInt() == 1);
    assert(multiply.rhs().as(NumericExpression.class).asInt() == 2);

    assert(divide.lhs().as(NumericExpression.class).asInt() == 3);
    assert(divide.rhs().as(NumericExpression.class).asInt() == 4);
  }
}
