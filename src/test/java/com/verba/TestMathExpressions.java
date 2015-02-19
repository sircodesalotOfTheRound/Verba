package com.verba;

import com.verba.language.parse.codestream.StringBasedCodeStream;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.language.parse.tokens.operators.mathop.AddOpToken;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/17.
 */
public class TestMathExpressions {
  @Test
  public void testMathExpressions() {
    StringBasedCodeStream codeStream = new StringBasedCodeStream("x + y + z");
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("testfile.v", codeStream);
    MathOpExpression expression = (MathOpExpression) RValueExpression.read(null, lexer);

    assert(expression.lhs().is(NamedValueExpression.class));
    assert(expression.operator().is(AddOpToken.class));
    assert(expression.rhs().is(NamedValueExpression.class));
  }
}
